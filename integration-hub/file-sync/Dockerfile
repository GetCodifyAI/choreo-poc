# Use the OpenJDK base image with Java 17
FROM openjdk:17-oracle

# Create a non-root user with a user ID between 10000 and 20000
RUN adduser --system --uid 15000 myuser

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/file-sync-0.0.1-SNAPSHOT.jar /app/

# Give ownership of the /app directory to the non-root user
RUN chown -R myuser:myuser /app

# Switch to the non-root user
USER 15000

# Expose the port that the application runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "file-sync-0.0.1-SNAPSHOT.jar"]
