curl -v "http://localhost:8080/event" -X POST -H "Content-Type: application/json" -d '{"msg":"Hello Knative!"}'

curl -v "http://slop.default.127.0.0.1.nip.io/event" -X POST -H "Content-Type: application/json" -d '{"msg":"Hello Knative!"}'

curl -v "http://default-broker.default.svc.cluster.local" \
-X POST \
-H "Ce-Specversion: 1.0" \
-H "Ce-Type: dev.knative.samples.helloworld" \
-H "Ce-Source: dev.knative.samples/helloworldsource" \
-H "Ce-Id: 536808d3-88be-4077-9d7a-a3f162705f79" \
-H "Content-Type: application/json" \
-d '{"msg":"Hello Knative!"}'


2021-10-20 15:17:00.168  INFO 1 --- [nio-8080-exec-9] c.e.o.CloudEventController              
: Body: {"message": "Ping Hello world!"}; 
Headers: {host=slop.default.svc.cluster.local, user-agent=Go-http-client/1.1, content-length=32, accept-encoding=gzip,
ce-id=3d0a6b0c-ee53-4b1e-b574-f6748e4f7465, ce-source=/apis/v1/namespaces/default/pingsources/test-ping-source,
ce-specversion=1.0, ce-time=2021-10-20T15:17:00.163051858Z, ce-type=dev.knative.sources.ping, content-type=application/json, 
forwarded=for=10.244.0.9;proto=http, k-proxy-request=activator, traceparent=00-fb918db469eb02b286a01f81f9030a4e-79c2cac9d0153e52-00,
x-forwarded-proto=http, x-request-id=05b4cf39-347d-4a31-a3e6-a2536d089ebe}
