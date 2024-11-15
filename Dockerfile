# Dockerfile for Spring Boot application

## Stage 1: Build the application
# Use maven 3.9.9 as base image
FROM maven:3.9.9-amazoncorretto-21-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the project files
COPY . .

# Build the project
RUN mvn clean package

## Stage 2: Run the application
# Use java 21 as base image
FROM amazoncorretto:21-alpine AS runner

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the builder stage to the runner stage
COPY --from=builder /app/target/*.jar its-rct-api-mock-latest.jar
COPY --from=builder /app/api-mock/ api-mock/

# Run the application
CMD ["java", "-jar", "its-rct-api-mock-latest.jar"]

