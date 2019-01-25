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

## Build

To run it the application you can

    mvn spring-boot:run

Now you can try endpoints on [](http://localhost:8080/api)

## Test 

To test it run

    mvn verify
