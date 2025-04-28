FROM openjdk:21-slim

WORKDIR /app

COPY target/users-service-0.0.1-SNAPSHOT.jar users-service.jar

ENV SERVER_PORT=8083
ENV SPRING_PROFILES_ACTIVE=docker
CMD ["java", "-jar", "users-service.jar"]