package io.javaoperatorsdk.operator.processing.event.internal;

import io.fabric8.kubernetes.client.CustomResource;
import io.javaoperatorsdk.operator.processing.ResourceCache;
import io.javaoperatorsdk.operator.processing.event.CustomResourceID;
import io.javaoperatorsdk.operator.processing.event.EventSource;

public interface BackboneCustomResourceEventSource<T extends CustomResource<?,?>> extends EventSource, ResourceCache<T> {

    void whitelistNextEvent(CustomResourceID customResourceID);

}
