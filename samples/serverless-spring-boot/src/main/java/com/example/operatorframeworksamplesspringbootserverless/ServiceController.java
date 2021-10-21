package com.example.operatorframeworksamplesspringbootserverless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javaoperatorsdk.operator.api.Context;
import io.javaoperatorsdk.operator.api.Controller;
import io.javaoperatorsdk.operator.api.ResourceController;
import io.javaoperatorsdk.operator.api.UpdateControl;

import com.example.operatorframeworksamplesspringbootserverless.resource.CustomService;

@Controller
public class ServiceController implements ResourceController<CustomService> {

  private final Logger log = LoggerFactory.getLogger(ServiceController.class);

  @Override
  public UpdateControl<CustomService> createOrUpdateResource(CustomService resource,
      Context<CustomService> context) {
    log.info("Reconciling resource: {}", resource);
    return UpdateControl.noUpdate();
  }
}
