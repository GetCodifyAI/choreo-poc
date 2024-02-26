# Use the OpenJDK base image with Java 17
# FROM openjdk:17-alpine, FROM openjdk:17-oracle, FROM openjdk:17-jdk-slim
FROM openjdk:17-oracle

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/file-sync-0.0.1-SNAPSHOT.jar /app/

# Expose the port that the application runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "file-sync-0.0.1-SNAPSHOT.jar"]
