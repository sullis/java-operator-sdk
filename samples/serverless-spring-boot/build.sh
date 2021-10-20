#!/bin/bash
mvn clean install
docker build . -t kind.local/slop:0.1.0
kind load docker-image kind.local/slop:0.1.0 --name knative
