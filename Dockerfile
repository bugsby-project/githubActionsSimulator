FROM gradle:7.4-jdk17 AS build

WORKDIR /buildApp
COPY . .
RUN gradle build --no-daemon

FROM openjdk:17.0-slim

EXPOSE 9090

USER root

RUN mkdir /app

COPY --from=build /buildApp/build/libs/*.jar /app/backend.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=compose", "-jar", "/app/backend.jar"]
