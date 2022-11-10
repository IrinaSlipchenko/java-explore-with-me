FROM bellsoft/liberica-openjdk-alpine:11.0.16.1-1
COPY target/*.jar ewm-service.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker", "/ewm-service.jar"]