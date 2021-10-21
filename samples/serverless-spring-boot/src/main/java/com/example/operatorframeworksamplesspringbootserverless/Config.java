package com.example.operatorframeworksamplesspringbootserverless;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.cloudevents.spring.mvc.CloudEventHttpMessageConverter;
import io.javaoperatorsdk.operator.Operator;
import io.javaoperatorsdk.operator.api.config.ConfigurationServiceOverrider;
import io.javaoperatorsdk.operator.config.runtime.DefaultConfigurationService;

@Configuration
public class Config implements WebMvcConfigurer {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(0, new CloudEventHttpMessageConverter());
  }

  @Bean(initMethod = "startServerless", destroyMethod = "close")
  public Operator operator() {
    Operator operator =
        new Operator(ConfigurationServiceOverrider.override(DefaultConfigurationService.instance())
            .withConcurrentReconciliationThreads(10).build());
    operator.registerServerless(new ServiceController(), null);
    return operator;
  }

}
