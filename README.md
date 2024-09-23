```markdown
# DocuMan

**DocuMan** is a Spring Boot-based REST API for managing documents and authors. The project includes CRUD operations for managing documents, authors, and other related entities. It also includes integrated Swagger documentation for easy API exploration.

## Features

- **CRUD operations** for documents and authors.
- **Validation** of inputs using Jakarta Bean Validation annotations.
- **Exception Handling** through a global exception handler.
- **Swagger UI** integration for API documentation and testing.
- **Spring Data JPA** for database interaction.
- **H2 Database** for in-memory testing.
- **Basic Authentication** for secured endpoints.

## Technologies

- **Java 21**
- **Spring Boot 3.3.3**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database** (for development and testing)
- **Swagger/OpenAPI 3.0**
- **Maven**

## Installation

### Prerequisites

- Java 21 or later
- Maven 3.8+
- Git

### Clone the Repository

```bash
git clone https://github.com/esinecan/DocuMan.git
cd DocuMan
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

Once the application is running, you can access it at:

```
http://localhost:8080
```

## API Documentation

The project includes Swagger/OpenAPI documentation, which can be accessed at:

```
http://localhost:8080/swagger-ui/index.html
```

This allows you to explore the API endpoints, send requests, and see detailed information about the request and response structure.

## API Endpoints

### Author Endpoints

| Method | Endpoint                   | Description                    |
|--------|----------------------------|--------------------------------|
| GET    | `/api/authors`              | Retrieve all authors           |
| GET    | `/api/authors/{id}`         | Retrieve an author by ID       |
| POST   | `/api/authors/add`          | Add a new author               |
| PUT    | `/api/authors/update/{id}`  | Update an existing author      |
| DELETE | `/api/authors/{id}`         | Delete an author by ID         |

### Document Endpoints

| Method | Endpoint                     | Description                      |
|--------|------------------------------|----------------------------------|
| GET    | `/api/documents`              | Retrieve all documents           |
| GET    | `/api/documents/{id}`         | Retrieve a document by ID        |
| POST   | `/api/documents/add`          | Add a new document               |
| PUT    | `/api/documents/update/{id}`  | Update an existing document      |
| DELETE | `/api/documents/{id}`         | Delete a document by ID          |

## Exception Handling

All exceptions are handled centrally by the `GlobalExceptionHandler` class. Custom exceptions like `ResourceNotFoundException` return meaningful HTTP status codes and messages.

### Error Response Structure

- **Message**: A description of the error.
- **Status**: HTTP status code.
- **Timestamp**: When the error occurred.

## Testing

Unit tests are provided for services and controllers using `MockMvc` and `Mockito`.

To run the tests:

```bash
mvn test
```

```
### Explanation of Key Sections:

- **Features**: Highlights the main features of the project.
- **Technologies**: Lists the tools and frameworks used in the project.
- **Installation**: Explains how to set up and run the project locally.
- **API Documentation**: Provides details on how to access the Swagger UI for exploring the API.
- **API Endpoints**: Lists all the main endpoints for managing authors and documents.
- **Exception Handling**: Describes the error-handling strategy used in the project.
- **Testing**: Instructions for running unit tests.
- **Contributing**: Guidelines on how to contribute to the project.
- **License**: Information about the project license.

Feel free to replace placeholders such as the email in the contact section with your actual details.
