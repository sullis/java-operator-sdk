#!/bin/bash
mvn clean install
kubectl apply -f target/classes/META-INF/fabric8/customservices.sample.javaoperatorsdk-v1.yml
docker build . -t kind.local/slop:0.1.0
kind load docker-image kind.local/slop:0.1.0 --name knative

