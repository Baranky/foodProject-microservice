
FROM openjdk:21
ARG JAR_FILE=target/*.jar
ENV SERVER_PORT=8082
COPY target/restaurant-service-0.0.1-SNAPSHOT.jar restaurant-service.jar
ENTRYPOINT ["java","-jar","restaurant-service.jar"]