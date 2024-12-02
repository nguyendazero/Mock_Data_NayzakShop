# Dockerfile for Spring Boot application

## Stage 1: Build the application
# Use maven 3.9.9 as base image
FROM maven:3.9.9-amazoncorretto-21-alpine AS haibazo-bff-mock-webapi-builder

# Set the working directory in the container
WORKDIR /app

# Copy the project files
COPY . .

# Build the project
RUN mvn clean package -pl haibazo-bff-mock-webapi

## Stage 2: Run the application
# Use java 21 as base image
FROM amazoncorretto:21-alpine AS haibazo-bff-mock-webapi-runner

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the haibazo-bff-mock-webapi-builder stage
COPY --from=haibazo-bff-mock-webapi-builder /app/haibazo-bff-mock-webapi/target/*.jar haibazo-bff-mock-webapi.jar
COPY --from=haibazo-bff-mock-webapi-builder /app/haibazo-bff-mock-static /app/haibazo-bff-mock-static

# Run the application
CMD ["java", "-jar", "haibazo-bff-mock-webapi.jar"]

