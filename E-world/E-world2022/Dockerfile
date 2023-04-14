FROM openjdk:17-alpine
WORKDIR /app
COPY target/eworld-0.0.1-SNAPSHOT.jar /app/eworld.jar
COPY src/main/resources /app/resources
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "eworld.jar"]


