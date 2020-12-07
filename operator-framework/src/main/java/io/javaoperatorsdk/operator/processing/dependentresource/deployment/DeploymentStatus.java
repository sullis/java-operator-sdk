package io.javaoperatorsdk.operator.processing.dependentresource.deployment;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.javaoperatorsdk.operator.processing.dependentresource.SimpleStatus;
import io.javaoperatorsdk.operator.processing.dependentresource.Status;

public class DeploymentStatus extends SimpleStatus {

    private final Deployment deployment;

    public DeploymentStatus(Status status, Deployment deployment) {
        super(status);
        this.deployment = deployment;
    }

    public Deployment getDeployment() {
        return deployment;
    }
}
