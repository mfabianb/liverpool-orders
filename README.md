# Liverpool Orders Management System

#### Author: Martin Fabian B. mfabianb08@gmail.com

## Overview

This project implements an Order Management System for Liverpool, allowing customer administration and 
order search capabilities through a RESTful API.

The solution integrates with external services that provide order and item information, persists customer 
data in MongoDB, and implements Elasticsearch to provide advanced search functionality including type-ahead, 
accent-insensitive, case-insensitive, and fuzzy matching searches.

The application was developed using Java 17, Spring Boot, MongoDB, Elasticsearch, and Hexagonal 
Architecture (Ports and Adapters).

---

# Solution Architecture

The application follows the principles of Hexagonal Architecture, separating business rules from infrastructure concerns.

## Layers

### Application Layer

Contains use cases and orchestration logic:

- Customer creation new information
- Customer get information
- Customer update information
- Order synchronization (merge orders and items from API Calls)
- Order search (using merged information)
- Repository ports
- External service ports

### Domain Layer

Contains the core business logic and contracts:

- Domain models
- Business exceptions

### Infrastructure Layer

Contains technical implementations:

- MongoDB adapters
- Elasticsearch adapters
- REST controllers
- External DTOs
- Configuration classes
- 
---

# Technologies Used

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot 3 | Application Framework |
| MongoDB | Customer Persistence |
| Elasticsearch | Order Search Engine |
| Spring Data MongoDB | MongoDB Integration |
| Spring Data Elasticsearch | Elasticsearch Integration |
| WebClient | External API Consumption |
| OpenAPI / Swagger | API Documentation |
| JUnit 5 | Unit Testing |
| Mockito | Mocking Framework |
| Docker | Containerization |
| Gradle | Build Tool |

---

# Customer Management

Customer information is persisted in MongoDB.

## Customer Document Structure

```json
{
  "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
  "firstName": "John",
  "lastName": "Doe",
  "middleName": "Smith",
  "email": "john.doe@email.com",
  "shippingAddress": "Mexico City",
  "orders": []
}
```

The `userId` is used as the business identifier and is linked to the external Orders API.

## Supported Operations

## Swagger UI
API documentation available at:  
[http://localhost:8081/liverpool-orders/swagger-ui/index.html](http://localhost:8081/liverpool-orders/swagger-ui/index.html)

The documentation includes:

- Endpoints
- Request examples
- Response examples
- Validation rules
- Error responses

---

### Create Customer
Creates a new customer.
```http
POST /api/v1/customers
```
```json
{
  "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
  "firstName": "John",
  "lastName": "Doe",
  "middleName": "Smith",
  "email": "john.doe@email.com",
  "shippingAddress": "Mexico City"
}
```

### Get Customer
Retrieves customer information.
```http
GET /api/v1/customers/{userId}
```


### Update Customer
Updates customer information, and associated orders or refreshes associated orders.
```http
PUT /api/v1/customers/{userId}
```
```json
{
  "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
  "firstName": "John",
  "lastName": "Doe",
  "middleName": "Smith",
  "email": "john.doe@email.com",
  "shippingAddress": "Mexico City"
}
```
---

# External Integrations

The application consumes two external services.

## Orders API

Provides customer orders.

```http
GET https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos
```

Returns:

- Order Reference
- User Identifier
- Sales Channel
- Order Status

Example:

```json
{
  "orderRef": "3010091676",
  "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
  "canal": "online",
  "orderStatus": "2025-12-06"
}
```

---

## Items API

Provides products associated with orders.

```http
GET https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items
```

Returns:

- Item Identifier
- Product Name
- Quantity

Example:

```json
{
  "itemId": "3010091676-1132351437",
  "quantity": 3,
  "displayName": "Pantalón Levi's"
}
```

---

# Order Synchronization

Order information is distributed across two independent services.

To support advanced searching, the application synchronizes data during startup.

## Synchronization Flow

```text
Orders API
      +
Items API
      ↓
Build Search Documents
      ↓
Index into Elasticsearch
```

The application retrieves data from both APIs, joins the information, and generates search-optimized documents.

Example indexed document:

```json
{
  "orderRef": "3010091676",
  "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
  "orderStatus": "2025-12-06",
  "channel": "online",
  "itemId": "3010091676-1132351437",
  "quantity": 3,
  "displayName": "Pantalón Levi's"
}
```

These documents are indexed into Elasticsearch and used exclusively for searching.

---

# Search Service

The search functionality is implemented using Elasticsearch.

## Endpoint

```http
GET /api/v1/orders/search?text={value}
```

## Searchable Fields

- orderRef
- orderStatus
- channel
- displayName

## Supported Features

### Type-Ahead Search

Example:

```text
api/v1/orders/search?orderRef=2025
```

Matches:

```text
api/v1/orders/search?orderRef=2025
```

### Case-Insensitive Search

```text
api/v1/orders/search?orderRef=2025
```

Produces identical results.

### Accent-Insensitive Search

```text
Pantalón
Pantalon
```

Produces identical results.

### Fuzzy Search

Minor spelling mistakes are tolerated.

Example:

```text
pantlon
```

Matches:

```text
Pantalón Levi's
```

---

# Running the Application

## Prerequisites

- Java 17
- Docker
- Docker Compose

---

## Run and deploy
### Docker Run commands
```
docker build . -t liverpool-orders
```
```
docker run -p 8081:8081 liverpool-orders
```

### In case Docker does not working, use gradle commands
```
./gradlew clean
```
```
./gradlew build
```
```
./gradlew bootRun
```

### The API will run on `http://localhost:8081/liverpool-orders/`.

---

# Testing

Unit tests were implemented using:

- JUnit 5
- Mockito
- Spring Boot Test

The test suite covers:

- Customer creation
- Customer retrieval
- Customer update
- Exception handling
- External API integration logic
- Search functionality

Target code coverage is above 90%.

---

# Design Decisions

## Why MongoDB?

Customer information is document-oriented and does not require complex relational modeling.

MongoDB provides:

- Flexible schema
- Fast development
- Natural representation of customer data
- Easy embedding of customer orders

## Why Elasticsearch?

The challenge requires:

- Type-ahead search
- Accent-insensitive search
- Case-insensitive search
- Fuzzy matching

Elasticsearch provides native support for all these requirements while delivering high-performance full-text search capabilities.

## Why Hexagonal Architecture?

Hexagonal Architecture promotes:

- Separation of concerns
- Maintainability
- Testability
- Framework independence
- Easier infrastructure replacement

Business rules remain isolated from technical implementations.

---

# Project Structure

```text
src/main/java

com.liverpool.orders

├── application
│   ├── mappers
│   ├── ports
│   ├── service
│   └── usecase
|
|
├── domain
│   ├── model
│   └── exceptions
│
│
└── infrastructure
    ├── adapters
    |   ├── api
    |   |   ├──controllers
    |   |   └──dto
    |   |
    |   |
    |   ├── elasticsearch
    |   |   ├──documents
    |   |   ├──dto
    |   |   └──repositories
    |   |
    |   |
    |   └── mongo
    |       ├──documents
    |       └──repositories
    |
    |
    └── config
```

---

# Future Improvements

- Use of CheckStyle
- Security implementation (OAuth)
- Cloud ready to use implementation
- Scheduled synchronization jobs
- Pagination and sorting
- Customer deletion endpoint
- Elasticsearch autocomplete analyzers
- Redis caching
- CI/CD pipeline
- Kubernetes deployment
- Observability with Prometheus and Grafana
