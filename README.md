# Order Processing System

Backend service for managing e-commerce orders.

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* H2 Database
* Maven
* Swagger
* JUnit
* Mockito


## Features

* Create order with multiple items
* Retrieve order by ID
* List all orders
* Filter by status
* Update order status
* Cancel order only when status is PENDING
* Background scheduler updates PENDING → PROCESSING every 5 minutes

## API Endpoints

POST /api/orders

GET /api/orders/{id}

GET /api/orders

PATCH /api/orders/{id}/status

DELETE /api/orders/{id}

## How to Run

mvn clean install

mvn spring-boot:run

Swagger:

[/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html#/)



H2 Console:
JDBC URL: jdbc:h2:mem:orderdb

URL: [/h2-console](http://localhost:8080/h2-console/)



