# Registration and RedirectRegistration Applications

## Overview

This project consists of two Spring Boot applications that demonstrate a basic RESTful setup with CRUD operations, DTO patterns, dynamic mapping using MapStruct, unit and integration testing, and logging through AOP.

## Applications

1. **Registration Application**: This application exposes CRUD endpoints for a `Registration` entity.
2. **RedirectRegistration Application**: This application consumes the endpoints exposed by the `Registration` application and re-exposes them on a different port.

## Features

### Registration Application

- **CRUD Operations**: Perform Create, Read, Update, and Delete operations on `Registration` entities.
- **DTO Pattern**: Use Data Transfer Objects (DTO) to interact with the client, ensuring entities are not exposed directly.
- **MapStruct**: Automatically map between entities and DTOs.
- **Validation**: Validate data during creation and update operations.
- **Logging with AOP**: Log method execution details in service classes.

### RedirectRegistration Application

- **Proxy Endpoints**: Proxy the endpoints exposed by the `Registration` application.
- **Logging**: Log method execution details.

## API Endpoints Overview

### Registration Service

The Registration Service handles CRUD operations for registrations. Below are the available endpoints:

- **List Registrations**
  - **Endpoint**: `GET /registration/list`
  - **Description**: Retrieves a list of all registrations.

- **Create Registration**
  - **Endpoint**: `POST /registration/`
  - **Description**: Creates a new registration.

- **Delete Registration**
  - **Endpoint**: `DELETE /registration/{id}`
  - **Description**: Deletes a registration by its ID.

- **Update Registration**
  - **Endpoint**: `PUT /registration/{id}`
  - **Description**: Updates an existing registration by its ID.

- **Partially Update Registration**
  - **Endpoint**: `PATCH /registration/{id}`
  - **Description**: Partially updates a registration by its ID.

### RedirectRegistration Service

The RedirectRegistration Service acts as a proxy to the Registration Service, forwarding requests to it. Below are the proxy endpoints:

- **Proxy List Registrations**
  - **Endpoint**: `GET /redirect/registration/list`
  - **Description**: Forwards the request to `GET /registration/list` of the Registration Service.

- **Proxy Create Registration**
  - **Endpoint**: `POST /redirect/registration/`
  - **Description**: Forwards the request to `POST /registration/` of the Registration Service.

- **Proxy Delete Registration**
  - **Endpoint**: `DELETE /redirect/registration/{id}`
  - **Description**: Forwards the request to `DELETE /registration/{id}` of the Registration Service.

- **Proxy Update Registration**
  - **Endpoint**: `PUT /redirect/registration/{id}`
  - **Description**: Forwards the request to `PUT /registration/{id}` of the Registration Service.

- **Proxy Partially Update Registration**
  - **Endpoint**: `PATCH /redirect/registration/{id}`
  - **Description**: Forwards the request to `PATCH /registration/{id}` of the Registration Service.


## Testing

- **Unit Tests**: Test service layer methods with rich scenarios.
- **Integration Tests**: Test controller layer methods.

## Logging

Logging is implemented in the service layer of both applications:
- Before method execution: Logs input parameters.
- After method execution: Logs return values and execution time.

## YouTube link

Detailed view on the applications: [youtube](https://your_link).


## Deployment

**Clone repository firstly**

```bash
    git clone <repository-url>
    cd <repository-directory>
```

**Build and Run the Registration Application**

```bash
    cd registration
    ./gradlew clean build
    ./gradlew bootRun
```

Port number is 8080

**Build and Run RedirectRegistration Application**:
```bash
    cd ../redirectregistration
    ./gradlew clean build
    ./gradlew bootRun
```
Port number is 8001



https://youtu.be/mzojEv7Agrk