# syntax=docker/dockerfile:1.4

### --- Stage 1: Builder ---
FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Leverage cache: first copy pom and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Now copy source and build
COPY src ./src
RUN mvn package -DskipTests

### --- Stage 2: Runtime ---
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
