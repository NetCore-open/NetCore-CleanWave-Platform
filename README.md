# CleanWave Platform (NetCore-CleanWave-Platform)

## Overview

CleanWave Platform is a Spring Boot service that provides a REST API designed under Clean Architecture and Domain-Driven Design (DDD) principles. The project is organized into bounded contexts and includes a shared kernel (`shared`) supporting internationalization (i18n), global REST exception handling, standard response adapters, custom database physical naming strategies, and Docker deployment capability.

## Technologies

- **Java 26** & **Spring Boot 4.0.6**
- **Spring Web** (REST controllers)
- **Spring Data JPA** (Hibernate) with MySQL
- **Spring Validation** (Jakarta Bean Validation)
- **SpringDoc OpenAPI 3** / Swagger UI
- **Lombok** (compile-time helpers)

## Prerequisites

- **OpenJDK 26**
- **MySQL 8+** (local or containerized database server)
- **Docker Desktop** (optional for local testing, recommended for deployment packaging)

## Environment variables

The application resolves database credentials and connection details from environment variables in production. Set the following before running the production profile:

| Variable            | Description                                             |
|---------------------|---------------------------------------------------------|
| `DATABASE_USER`     | MySQL username                                          |
| `DATABASE_PASSWORD` | MySQL password                                          |
| `DATABASE_URL`      | Database host / hostname used by the production profile |
| `DATABASE_NAME`     | Database name (default: `cleanwave-os`)                 |
| `DATABASE_PORT`     | Database port (production profile)                      |

## Spring profiles

| Profile     | Usage                                                                  |
|-------------|------------------------------------------------------------------------|
| *(default)* | Uses `localhost:3306` with SSL enabled; SQL logging off                |
| `dev`       | Uses `localhost:3306` with SSL disabled; SQL logging on; auto-updates schema |
| `prod`      | Builds the JDBC URL from environment variables; used in Docker/CI       |

Set the active profile using:

```bash
export SPRING_PROFILES_ACTIVE=dev
```

## API documentation (Swagger UI)

When the application is running, interactive API documentation is available at:

```
http://localhost:8080/swagger-ui/index.html
```

## Getting started (quick)

Run the project locally using the Maven wrapper with the `dev` profile:

```bash
./mvnw spring-boot:run
```

For the `prod` profile, make sure to supply the required environment variables:

```bash
export DATABASE_USER=your_db_user
export DATABASE_PASSWORD=your_db_password
export DATABASE_URL=your_db_host
export DATABASE_PORT=3306
export DATABASE_NAME=cleanwave-os
export SPRING_PROFILES_ACTIVE=prod
java -jar target/*.jar
```

## Run with Docker

Build the container image:

```bash
docker build -t cleanwave-platform:local .
```

Run the container using `docker-compose`:

```bash
docker compose up --build
```
