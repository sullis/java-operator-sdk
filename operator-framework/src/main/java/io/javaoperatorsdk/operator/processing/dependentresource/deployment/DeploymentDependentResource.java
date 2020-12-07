package io.javaoperatorsdk.operator.processing.dependentresource.deployment;

import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.utils.Serialization;
import io.javaoperatorsdk.operator.processing.dependentresource.DependentResource;
import io.javaoperatorsdk.operator.processing.dependentresource.Status;
import io.javaoperatorsdk.operator.processing.event.AbstractEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static io.javaoperatorsdk.operator.processing.KubernetesResourceUtils.getUID;
import static io.javaoperatorsdk.operator.processing.KubernetesResourceUtils.getVersion;
import static java.net.HttpURLConnection.HTTP_GONE;

public class DeploymentDependentResource extends AbstractEventSource
        implements DependentResource<DeploymentInput, DeploymentStatus>, Watcher<Deployment> {

    private final static Logger log = LoggerFactory.getLogger(DeploymentDependentResource.class);

    private final KubernetesClient client;
    private final Map<String, Deployment> deploymentCache = new ConcurrentHashMap<>();

    public DeploymentDependentResource(KubernetesClient kubernetesClient) {
        this.client = kubernetesClient;
        registerWatch();
    }

    private void registerWatch() {
        client.apps().deployments().inAnyNamespace().withLabel("managed-by", "tomcat-operator").watch(this);
    }


    @Override
    public DeploymentStatus reconcile(DeploymentInput input) {

        Optional<Deployment> cachedDeployment = getLatestDeployment(input.getCustomResourceUid());
        Deployment deployment;
        if (cachedDeployment.isEmpty() || !isDeploymentAccordingToInput(input, cachedDeployment.get())) {
            deployment = createOrUpdateDeployment(input);
        } else {
            deployment = cachedDeployment.get();
        }

        return new DeploymentStatus(isDeploymentAccordingToInput(input, deployment) ?
                Status.CREATED_SUCCESSFULLY : Status.IN_PROGRESS, deployment);
    }

    private Deployment createOrUpdateDeployment(DeploymentInput input) {
        String ns = input.getNamespace();
        Deployment existingDeployment = client.apps().deployments()
                .inNamespace(ns).withName(input.getName())
                .get();
        if (existingDeployment == null) {
            Deployment deployment = loadYaml(Deployment.class, "deployment.yaml");
            deployment.getMetadata().setName(input.getName());
            deployment.getMetadata().setNamespace(ns);
            deployment.getMetadata().getLabels().put("created-by", input.getName());
            deployment.getMetadata().getLabels().put("managed-by", "tomcat-operator");
            // set tomcat version
            deployment.getSpec().getTemplate().getSpec().getContainers().get(0).setImage(input.image());
            deployment.getSpec().setReplicas(input.getReplicaCount());

            //make sure label selector matches label (which has to be matched by service selector too)
            deployment.getSpec().getTemplate().getMetadata().getLabels().put("app", input.getName());
            deployment.getSpec().getSelector().getMatchLabels().put("app",input.getName());

            OwnerReference ownerReference = deployment.getMetadata().getOwnerReferences().get(0);
            ownerReference.setName(input.getName());
            ownerReference.setUid(input.getCustomResourceUid());

            log.info("Creating or updating Deployment {} in {}", deployment.getMetadata().getName(), ns);
            return client.apps().deployments().inNamespace(ns).create(deployment);
        } else {
            existingDeployment.getSpec().getTemplate().getSpec().getContainers().get(0).setImage(input.image());
            existingDeployment.getSpec().setReplicas(input.getReplicaCount());
            return client.apps().deployments().inNamespace(ns).createOrReplace(existingDeployment);
        }
    }

    private boolean isDeploymentAccordingToInput(DeploymentInput input, Deployment deployment) {
        io.fabric8.kubernetes.api.model.apps.DeploymentStatus deploymentStatus = Objects.requireNonNullElse(deployment.getStatus(), new io.fabric8.kubernetes.api.model.apps.DeploymentStatus());
        String deploymentImage = deployment.getSpec().getTemplate().getSpec()
                .getContainers().get(0).getImage();
        return input.getReplicaCount() == Objects.requireNonNullElse(deploymentStatus.getReadyReplicas(), 0)
                && input.image().equals(deploymentImage);

    }


    public Optional<Deployment> getLatestDeployment(String ownerUID) {
        return Optional.ofNullable(deploymentCache.get(ownerUID));
    }

    @Override
    public void eventReceived(Watcher.Action action, Deployment deployment) {
        log.info("Event received for action: {}, Deployment: {} (rr={})", action.name(), deployment.getMetadata().getName(), deployment.getStatus().getReadyReplicas());
        if (action == Action.ERROR) {
            log.warn("Skipping {} event for custom resource uid: {}, version: {}", action,
                    getUID(deployment), getVersion(deployment));
            return;
        }
        deploymentCache.put(deployment.getMetadata().getOwnerReferences().get(0).getUid(), deployment);
        eventHandler.handleEvent(new DeploymentEvent(action, deployment, this));
    }

    @Override
    public void onClose(KubernetesClientException e) {
        if (e == null) {
            return;
        }
        if (e.getCode() == HTTP_GONE) {
            log.warn("Received error for watch, will try to reconnect.", e);
            registerWatch();
        } else {
            // Note that this should not happen normally, since fabric8 client handles reconnect.
            // In case it tries to reconnect this method is not called.
            log.error("Unexpected error happened with watch. Will exit.", e);
            System.exit(1);
        }
    }

    @Override
    public void eventSourceDeRegisteredForResource(String ownerUID) {
        deploymentCache.remove(ownerUID);
    }

    private <T> T loadYaml(Class<T> clazz, String yaml) {
        try (InputStream is = getClass().getResourceAsStream(yaml)) {
            return Serialization.unmarshal(is, clazz);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot find yaml on classpath: " + yaml);
        }
    }

}
