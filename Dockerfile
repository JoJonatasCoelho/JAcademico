# Dockerfile: builds the Spring Boot application container for docker-compose.
# Build stage: uses Maven image with JDK 17 to compile the app
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

# Runtime stage: slim JRE image
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the repackaged Spring Boot fat jar (pattern avoids name mismatch issues)
COPY --from=build /app/target/*SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=docker"]
