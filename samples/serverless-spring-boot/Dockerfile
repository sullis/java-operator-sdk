FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY target/operator.jar /opt/app/operator.jar
CMD ["java", "-jar", "/opt/app/operator.jar"]