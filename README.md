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
- Docker (optional)

# Quick Start

## Run

In default mode application could be run by

    mvn spring-boot:run

Now you can try endpoints on [](http://localhost:8080/api).
Documentation for API is available at
[](http://localhost:8080/swagger-ui.html).


### Using PostgreSQL

By default application uses H2 in memory database. If you want to use
PostreSQL, you should have database server instance available (for now
we use it with user `postgres` and database `postgres` and default port 5432).

    DB_HOST=localhost mvn -Dspring.profiles.active=PROD spring-boot:run

In this command we run line we specify `DB_HOST` to be `localhost`, if
you have another host name for Postgresql instance, change accordingly. 


# Development 

## Run

As mentioned in the [Quick Start](#quick-start) you can run application by 

    mvn spring-boot:run

## Test 

To test it run

    mvn verify

# Production

## Build

We provide containerized environment setup suitable for production. For running this environment locally
you need a Docker installed. First build the application by running

    mvn package


Now you can get the application stack up and running by 

    docker-compose up

Optionally you can supply `-d` parameter to run it in the daemon mode. 

## Publishing and Deployment 

After you tested Docker based containerized setup locally you can
deploy it. Image with application is available under
`demo_app:latest`. Tag it and push it to the registry or deploy it on
the cloud. `docker-compose.yml` file should give you an idea on
configuring containers on the cloud or Kubernetes setup.
