# Simple Spring Boot Based Application

This application is a simple server with following CRUD and search
endpoints. It is supposed to demo various aspects of Spring Boot
application.

## Application Checklist

- REST endpoints for CRUD operations on the model
- Database Model for Patient, Address and Medical Record
- Swagger documentaion for REST endpoints 

## Requirements 

- JDK 1.8+
- Maven 3+

## Run 

To run it the application you can

    mvn spring-boot:run

Now you can try endpoints on [](http://localhost:8080/api)

## Test 

To test it run

    mvn verify

## Build and Package 

For running application with Postgresql you need a Docker installed.
First build the application by running

    mvn package
    
Now you can get the application stack up and running by 

    docker-compose up

This will get both Postgresql and OpenJDK based containers for
database and for the application.

### Technical Aspects

Java application expects the database host to be in the `DB_HOST`
environment variable. If it is not set then it uses `localhost`. By
default Postgresql port is 5432.
