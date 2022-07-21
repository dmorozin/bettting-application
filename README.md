# Betting Application
Angular application with Spring Boot API and PostgreSQL database.

Follow the steps below to run the application locally in your environment with your settings.

## Configuration
Configuration can be found in `betting-api/src/main/resources/application.yml`. 
Before running application, create new database `betting_app`.

PostgreSQL related properties have to be changed to run the application in your local environment.
In `application.yml` change properties `spring.datasource.username` and `spring.datasource.password` to match yours.

Same has to be done in `betting-api/src/main/resources/liquibase.properties` file for properties `username` and `password`. 

Change datasource url port if it's not the same as in yml (`jdbc:postgresql://localhost:5432/betting_app`)

## Running the application
Backend server can be run with the command `mvn spring-boot:run`. Data will be generated on the first server start up.
To run Angular application first run `npm install` inside `betting-ui` then `npm start`.
Open application in browser on http://localhost:4200/