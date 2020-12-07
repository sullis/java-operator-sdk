package io.javaoperatorsdk.operator.processing.dependentresource;

import io.fabric8.kubernetes.client.CustomResource;
import io.javaoperatorsdk.operator.api.DeleteControl;
import io.javaoperatorsdk.operator.processing.event.EventSource;

public interface DependentResource<T,L, K extends StatusDescriptor> {

    K createOrUpdate(T input);

    DeleteControl delete(L input);

}
