package io.javaoperatorsdk.operator.processing.dependentresource;

import io.fabric8.kubernetes.client.CustomResource;
import io.javaoperatorsdk.operator.processing.event.EventSource;

public interface DependentResource<T, K extends StatusDescriptor> {

    K reconcile(T input);

}
