# Use JDK 17 as the base image for building
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Copy folder into the Docker image
WORKDIR /opt/app
COPY ./ /opt/app

# Build the application using Maven with JDK 17
RUN mvn clean install -DskipTests

# Use JDK 17 as the base image for running the application
FROM eclipse-temurin:17-jre-alpine

# Copy the compiled JAR file from the build stage
COPY --from=build /opt/app/target/*.jar app.jar

EXPOSE 8086

VOLUME /opt/app/logs

# Specify the command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
