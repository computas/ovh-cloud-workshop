# Use the official Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory inside the build container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK image as the base image for the runtime
FROM openjdk:21-jdk-slim

# Set the working directory inside the runtime container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
