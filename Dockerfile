# Use a lightweight Java image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built jar into the container
COPY target/*.jar app.jar

# Expose port 8080 (or your Spring Boot port)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
