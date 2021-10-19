package io.javaoperatorsdk.operator.processing.event.internal;

import io.cloudevents.CloudEvent;

public interface ServerlessEventProcessor {

    void processCloudEvent(CloudEvent cloudEvent);

}
