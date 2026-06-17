# Dockerfile for cleanwave-platform
# Summary:
# This Dockerfile builds and runs the cleanwave-platform application using the Maven Wrapper and OpenJDK 26 (Temurin).
# Description:
# Uses eclipse-temurin:26-jdk for building (with the project's own Maven Wrapper ./mvnw)
# and eclipse-temurin:26-jre for a lean runtime image. This avoids depending on
# the maven:x.x-eclipse-temurin-26 image, which is not yet available on Docker Hub.
# Version: 1.1
# Maintainer: Open-source Development Team

# Step 1: Build the application using the Maven Wrapper and Temurin JDK 26
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
# Copy the source code into the container
COPY src ./src
# Build the application
RUN ./mvnw clean package -DskipTests -B

# Step 2: Create a lean runtime image with Temurin JRE 26
FROM eclipse-temurin:26-jre AS runtime
ENV SPRING_PROFILES_ACTIVE=prod
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Step 3: Configure and run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Note: The application will run with the 'prod' profile as set in the runtime stage.
# Required environment variables in Railway:
# - MYSQLHOST, MYSQLPORT, MYSQLUSER, MYSQLPASSWORD, MYSQLDATABASE (auto-injected by Railway MySQL service)
# - JWT_SECRET: A secure secret string for JWT signing.
# - PORT: The port on which the application will run (auto-injected by Railway).
