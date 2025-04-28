
FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY target/shippping-service-0.0.1-SNAPSHOT.jar shippping-service.jar
ENTRYPOINT ["java","-jar","shippping-service.jar"]