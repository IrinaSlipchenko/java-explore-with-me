FROM bellsoft/liberica-openjdk-alpine:11.0.16.1-1
COPY target/*.jar stats-server.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker", "/stats-server.jar"]