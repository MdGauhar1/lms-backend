# Use Java 17
FROM eclipse-temurin:17-jdk

# App folder
WORKDIR /app

# Copy jar
COPY target/*.jar app.jar

# Run app
ENTRYPOINT ["java","-jar","app.jar"]
