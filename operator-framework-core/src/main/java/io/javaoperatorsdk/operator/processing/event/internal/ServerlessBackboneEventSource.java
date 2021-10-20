package io.javaoperatorsdk.operator.processing.event.internal;

import java.util.Optional;
import java.util.function.Function;

import io.cloudevents.CloudEvent;
import io.fabric8.kubernetes.client.CustomResource;
import io.javaoperatorsdk.operator.processing.ConfiguredController;
import io.javaoperatorsdk.operator.processing.event.AbstractEventSource;
import io.javaoperatorsdk.operator.processing.event.CustomResourceID;
import io.javaoperatorsdk.operator.processing.event.DefaultEvent;

/**
 *
 * @param <T> - custom resource type to bound
 */
public class ServerlessBackboneEventSource<T extends CustomResource<?, ?>>
    extends AbstractEventSource
    implements BackboneCustomResourceEventSource<T>, ServerlessEventProcessor {

  private ConfiguredController<T> controller;
  // here we need to create also CustomResourceEvents
  private Function<CloudEvent, DefaultEvent> eventMapper;
  // private Map<CustomResourceID,CloudEvent> underProcessing;
  // private Map<CustomResourceID,CloudEvent> submittedForProcessing;

  public ServerlessBackboneEventSource(ConfiguredController<T> controller) {
    this.controller = controller;
  }

  // todo concurrency for same CR
  // todo map to custom resource event
  @Override
  public void processCloudEvent(CloudEvent cloudEvent) {
    eventHandler.handleEvent(eventMapper.apply(cloudEvent));
  }

  // we should consider caching / watching here for the target custom resource changes?!
  @Override
  public Optional<T> getCustomResource(CustomResourceID resourceID) {
    return Optional
        .ofNullable(controller.getCRClient().inNamespace(resourceID.getNamespace().orElse(null))
            .withName(resourceID.getName()).get());
  }

  @Override
  public void whitelistNextEvent(CustomResourceID customResourceID) {}
}
