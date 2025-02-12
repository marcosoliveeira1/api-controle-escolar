# Controle Escolar API (School Control API)

## Description

This API is for a School Control System, allowing management of schools, students, and student access records.

## Technologies Used

*   Java 23
*   Spring Boot
*   Spring Data JPA (for database persistence)
*   H2 Database (in-memory - configured in `backend/src/main/resources/application.properties`)
*   ModelMapper (for DTO to Entity mapping)
*   Swagger (for API documentation - OpenAPI 3)
*   Jackson (for JSON and XML serialization)
*   Gradle (with Kotlin DSL)
*   Docker (optional, for containerization)

## Prerequisites

*   Java Development Kit (JDK) 23 installed
*   Gradle installed (or use the Gradle wrapper `./gradlew` included in the project)
*   Docker Desktop installed (if you want to run with Docker)

## Running the Application Locally (Without Docker)

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/marcosoliveeira1/api-controle-escolar.git
    cd api-controle-escolar/backend
    ```
2.  **Build the application using Gradle:**
    ```bash
    ./gradlew clean build
    ```
3.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```
    The application will be accessible at `http://localhost:8080`.

## Running the Application with Docker

1.  **Ensure Docker Desktop is running.**
2.  **Navigate to the project root directory (`api-controle-escolar`) in your terminal.**
3.  **Build and run the Docker containers using Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    The application will be accessible at `http://localhost:8080`.

## API Endpoints

**Schools API (`/api/schools`)**

*   **POST /api/schools:** Create a new school.
    *   Request Body: `SchoolDTO` (JSON or XML)
    *   Response Body: `SchoolDTO` (JSON or XML) - Status 201 (Created)
*   **GET /api/schools/{id}:** Get a school by ID.
    *   Response Body: `SchoolDTO` (JSON or XML) - Status 200 (OK)
*   **GET /api/schools:** List all schools (paginated).
    *   Query Parameters: `page`, `size`, `sortBy` (optional, defaults provided)
    *   Response Body: Page of `SchoolDTO` (JSON or XML) - Status 200 (OK)
*   **PUT /api/schools/{id}:** Update a school.
    *   Request Body: `SchoolDTO` (JSON or XML)
    *   Response Body: `SchoolDTO` (JSON or XML) - Status 200 (OK)
*   **DELETE /api/schools/{id}:** Delete a school.
    *   Response Body: None - Status 204 (No Content)

**Students API (`/api/students`)**

*   **POST /api/students:** Create a new student.
    *   Request Body: `StudentDTO` (JSON or XML)
    *   Response Body: `StudentDTO` (JSON or XML) - Status 201 (Created)
*   **GET /api/students/{id}:** Get a student by ID.
    *   Response Body: `StudentDTO` (JSON or XML) - Status 200 (OK)
*   **GET /api/students:** List all students (paginated).
    *   Query Parameters: `page`, `size`, `sortBy` (optional, defaults provided)
    *   Response Body: Page of `StudentDTO` (JSON or XML) - Status 200 (OK)
*   **PUT /api/students/{id}:** Update a student.
    *   Request Body: `StudentDTO` (JSON or XML)
    *   Response Body: `StudentDTO` (JSON or XML) - Status 200 (OK)
*   **DELETE /api/students/{id}:** Delete a student.
    *   Response Body: None - Status 204 (No Content)

**Student Access API (`/api/access`)**

*   **POST /api/access/entry/{studentId}:** Register student entry.
    *   Response Body: `StudentAccessDTO` (JSON or XML) - Status 201 (Created)
*   **POST /api/access/exit/{studentId}:** Register student exit.
    *   Response Body: `StudentAccessDTO` (JSON or XML) - Status 200 (OK)

**Swagger UI (API Documentation)**

*   Access Swagger UI at: `http://localhost:8080/swagger-ui/index.html` to explore API documentation interactively.


## Database Configuration

*   The application uses an **in-memory H2 database**. Configuration is in `backend/src/main/resources/application.properties`. Data will be stored in a file-based database in the `./data/db` directory relative to where you run the application from (`backend` directory when running locally, and inside the Docker container when using Docker).