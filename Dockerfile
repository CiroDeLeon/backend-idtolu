# Use the OpenJDK 14 Alpine image as the base image
FROM openjdk:14-alpine

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper to the working directory
COPY .mvn .
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

# Copy the rest of the project files to the working directory
COPY . .

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

RUN apk update && apk add curl

RUN ./mvnw clean
# Run Maven command to compile the project and build an executable JAR
RUN ./mvnw package

# Specify the command to run when the Docker container starts
CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]