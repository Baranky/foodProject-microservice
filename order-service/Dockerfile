
FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-jar","order-service.jar"]