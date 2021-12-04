# Spring Boot Todo App

Spring Boot application that allows users to manage todo lists by adding, editing, and deleting lists and their corresponding items.

## Local Run

In order to run the app locally, use `mvnw spring-boot:run` command. The app run on localhost port 8080.

## Data

The application uses H2 in memory database and is seeded with sample data in the `src/main/resources/data.sql` file for ease of use.

## Testing

A Postman collection with sample requests has been included for lists and items.

Tests can be run via `mvnw test`