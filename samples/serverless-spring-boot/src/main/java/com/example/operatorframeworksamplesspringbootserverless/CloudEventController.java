package com.example.operatorframeworksamplesspringbootserverless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.cloudevents.CloudEvent;
import io.javaoperatorsdk.operator.Operator;
import io.javaoperatorsdk.operator.processing.event.internal.ServerlessEventProcessor;

@RestController
public class CloudEventController {

  private static final Logger log = LoggerFactory.getLogger(CloudEventController.class);

  private final Operator operator;
  private final ServerlessEventProcessor serverlessEventProcessor;

  public CloudEventController(Operator operator) {
    this.operator = operator;
    serverlessEventProcessor =
        (ServerlessEventProcessor) operator.getProcessors().values().toArray()[0];
  }

  @PostMapping("/event")
  public void processEvent(@RequestBody CloudEvent event) {
    log.info("Event: {}", event);

    // serverlessEventProcessor.processCloudEvent();
  }

}
