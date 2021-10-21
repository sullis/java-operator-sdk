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



Body: {"kind":"CustomService","namespace":"default","name":"custom-service1","apiVersion":"sample.javaoperatorsdk/v1"}; 
Headers: {host=slop.default.svc.cluster.local, user-agent=Go-http-client/1.1, content-length=112, accept-encoding=gzip,
ce-id=9a7de224-0b64-4880-b3c6-13566e2c8233, ce-kind=CustomService, ce-name=custom-service1, 
ce-namespace=default, ce-source=https://10.96.0.1:443, ce-specversion=1.0,
ce-subject=/apis/sample.javaoperatorsdk/v1/namespaces/default/customservices/custom-service1,
ce-time=2021-10-21T13:44:16.238623603Z, ce-type=dev.knative.apiserver.ref.add, content-type=application/json,
forwarded=for=10.244.0.2;proto=http, k-proxy-request=activator, traceparent=00-c06e9f1a4231ef283baeea6ed6edfeef-e1797603d43519df-00,
x-forwarded-proto=http, x-request-id=954cfddd-8e22-40a1-99a9-749704c2018b}


 : Body: {"kind":"CustomService","namespace":"default","name":"custom-service1","apiVersion":"sample.javaoperatorsdk/v1"};
 Headers: {host=slop.default.svc.cluster.local, user-agent=Go-http-client/1.1, content-length=112, 
 accept-encoding=gzip, ce-id=ecf52344-9ae7-4f3e-bc56-ec62ae22648a, ce-kind=CustomService, ce-name=custom-service1, 
 ce-namespace=default, ce-source=https://10.96.0.1:443, ce-specversion=1.0, 
 ce-subject=/apis/sample.javaoperatorsdk/v1/namespaces/default/customservices/custom-service1, 
 ce-time=2021-10-21T13:46:17.078661886Z, ce-type=dev.knative.apiserver.ref.update, content-type=application/json, 
 forwarded=for=10.244.0.2;proto=http, k-proxy-request=activator, traceparent=00-11f6412908c26a7045bb4056f7511f86-a3825a8da62f1319-00,
 x-forwarded-proto=http, x-request-id=71bf3711-260f-4fb1-aa71-1d3a45650266}
