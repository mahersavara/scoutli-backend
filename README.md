# Scoutli Backend (Quarkus)

This repository contains the backend API for the Scoutli application, built with **Java** and **Quarkus**, following **Clean Architecture** principles.

## Technology Stack

*   **Language:** Java 21
*   **Framework:** [Quarkus](https://quarkus.io/) (Supersonic Subatomic Java)
*   **Database:** PostgreSQL
*   **Build Tool:** Maven
*   **Containerization:** Docker (Native Image support via GraalVM)

## Project Structure (Clean Architecture)

The project is organized to separate concerns and make the codebase maintainable:

```
src/main/java/com/scoutli/
├── api/                # Infrastructure Layer (Inbound)
│   ├── controller/     # JAX-RS Resources (REST Endpoints)
│   └── dto/            # Data Transfer Objects (Request/Response models)
├── service/            # Application Layer
│   └── ...             # Business logic / Use cases implementation
├── domain/             # Domain Layer (Core)
│   ├── entity/         # JPA Entities (Rich Domain Models)
│   └── repository/     # Interface for data access (Panache Repositories)
└── config/             # Framework configuration
```

## Getting Started

### Prerequisites

*   Java JDK 21 installed.
*   Maven installed.
*   Docker (for building native images).

### Running in Development Mode

Quarkus comes with an amazing dev mode that supports live coding.

```bash
./mvnw quarkus:dev
```

The application will be available at `http://localhost:8080`.

### Building the Application

**JVM JAR:**
```bash
./mvnw package
```

**Native Image (Docker):**
```bash
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

## CI/CD

This repository is integrated with GitHub Actions to automatically build the native Docker image and push it to AWS ECR upon every commit to the `main` branch.
