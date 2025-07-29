#
# Stage 1: Build the Spring Boot application
#
FROM maven:3.8.7-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files (pom.xml) first to leverage Docker cache
# if only source code changes
COPY pom.xml .

# Copy the rest of the application source code
COPY src ./src

# Build the application
# -DskipTests: Skips running tests to speed up the build in Docker,
#              consider running tests in a separate CI/CD step.
# -Dspring-boot.repackage.skip: Prevents Spring Boot from creating the executable JAR
#                               in this step, as we'll do it explicitly later.
#                               (Not strictly necessary if `clean install` makes the jar,
#                               but good practice if you want fine-grained control).
RUN mvn clean install -DskipTests

#
# Stage 2: Create the final lightweight runtime image
#
FROM openjdk:17-jre-slim

# Set the working directory in the runtime image
WORKDIR /app

# Copy the built JAR file from the 'build' stage
# The JAR file is typically found in target/
# Replace 'smart-clinic-app.jar' with your actual JAR name if it's different
# You can find the exact JAR name in your pom.xml under <build><finalName> or by checking your local target/ directory.
COPY --from=build /app/target/*.jar smart-clinic-app.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Define the command to run your application
# Use ENTRYPOINT for the main command and CMD for default arguments,
# allowing them to be overridden easily.
ENTRYPOINT ["java", "-jar", "smart-clinic-app.jar"]

# Optional: If your application needs specific Spring profiles or arguments,
# you can add them to CMD. For example, to run with a 'prod' profile:
# CMD ["java", "-jar", "smart-clinic-app.jar", "--spring.profiles.active=prod"]