# Use an Alpine Linux-based image
FROM alpine:latest

# Update package lists and install curl
RUN apk update && \
    apk add --no-cache curl

FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:17.0.15

WORKDIR /app
COPY --from=build /build/target/users-backend-service-1.0.0.jar /app/

ARG PROFILE=dev
ARG APP_VERSION=1.0.0

EXPOSE 8080

ENV APP_ENVIRONMENT="dev"
ENV JDBC_URL="jdbc_url_is_missing"
ENV DB_USERNAME="db_username_is_missing"
ENV DB_PASSWORD="db_password_is_missing"
ENV OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI="missing_jwt_issuer_uri"

ENTRYPOINT ["java", "-jar", "users-backend-service-1.0.0.jar", \
            "--spring.profiles.active=${APP_ENVIRONMENT}", \
            "--spring.datasource.url=${JDBC_URL}", \
            "--spring.datasource.username=${DB_USERNAME}", \
            "--spring.datasource.password=${DB_PASSWORD}", \
            "--spring.security.oauth2.resourceserver.jwt.issuer-uri=${OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI}"]
