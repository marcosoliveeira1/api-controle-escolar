# BFF - Controle Escolar (School Control BFF)

## Description

This project is a Backend For Frontend (BFF) application built with NestJS and GraphQL. It serves as an intermediary layer between a frontend application (not included in this repository) and a backend REST API. The BFF aggregates data from the backend API and exposes it through a GraphQL interface, tailored to the needs of the frontend.

This specific BFF project focuses on managing school data, including students, schools, and student access records.

## Technologies Used

*   **Backend Framework:** [NestJS](https://nestjs.com/) - A progressive Node.js framework for building efficient, scalable, and enterprise-grade server-side applications.
*   **GraphQL:** [GraphQL](https://graphql.org/) - A query language for your API, and a server-side runtime for executing queries by using a type system you define for your data.
*   **GraphQL Driver:** [@nestjs/apollo](https://github.com/nestjs/graphql/tree/master/lib/drivers/apollo) -  NestJS integration with Apollo GraphQL Server.
*   **HTTP Client:** [@nestjs/axios](https://github.com/nestjs/axios) -  NestJS module for making HTTP requests using Axios.
*   **Validation:** [class-validator](https://github.com/typestack/class-validator) -  JavaScript library for object validation.
*   **Testing:** [Jest](https://jestjs.io/) -  A delightful JavaScript Testing Framework with a focus on simplicity.
*   **Containerization:** [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) - For containerizing and orchestrating the application.
*   **Language:** [TypeScript](https://www.typescriptlang.org/) -  A strongly typed superset of JavaScript that compiles to plain JavaScript.

## Prerequisites

Before running the application, ensure you have the following installed:

*   **Node.js** (>= 20.x) - [https://nodejs.org/](https://nodejs.org/)
*   **npm** (Node Package Manager) or **yarn** - [https://www.npmjs.com/](https://www.npmjs.com/) or [https://yarnpkg.com/](https://yarnpkg.com/)
*   **Docker** - [https://www.docker.com/get-started](https://www.docker.com/get-started) (If you want to run with Docker Compose)
*   **Docker Compose** - [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/) (If you want to run with Docker Compose)

## Local Development Setup

Follow these steps to run the BFF application locally:

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/marcosoliveeira1/api-controle-escolar.git
    cd bff-controle-escolar
    ```

2.  **Install dependencies:**

    ```bash
    npm install  # or yarn install
    ```

3.  **Environment Configuration:**

    Create a `.env` file in the root directory of the project and configure the following environment variables:

    ```env
    API_URL=http://localhost:8080/api # URL of the backend REST API
    PORT=8081                      # Port for the BFF application (optional, default is 3000)
    ```

    **Note:**  Ensure that the backend API service is running and accessible at the URL specified in `API_URL`. By default, the application is configured to connect to a backend API running at `http://localhost:8080/api`.

4.  **Run the application in development mode:**

    ```bash
    npm run start:dev  # or yarn start:dev
    ```

    This command will start the NestJS application in watch mode. You can access the GraphQL Playground at `http://localhost:8081/graphql` (or the port you configured in `.env`).

## API Routes (GraphQL)

Once the application is running, you can access the GraphQL Playground to explore the API. Here's a brief overview of the available queries and mutations:

**Student Module:**

*   **Queries:**
    *   `student(id: Int!): StudentDTO` - Retrieves a student by ID.
    *   `students(page: Int, size: Int, sortBy: String): StudentPage` - Retrieves a paginated list of students.

*   **Mutations:**
    *   `createStudent(createStudentInput: CreateStudentInput!): StudentDTO` - Creates a new student.
    *   `updateStudent(id: Int!, updateStudentInput: UpdateStudentInput!): StudentDTO` - Updates an existing student.
    *   `deleteStudent(id: Int!): Boolean` - Deletes a student.

**School Module:**

*   **Queries:**
    *   `school(id: Int!): SchoolDTO` - Retrieves a school by ID.
    *   `schools(page: Int, size: Int, sortBy: String): SchoolPage` - Retrieves a paginated list of schools.

*   **Mutations:**
    *   `createSchool(createSchoolInput: CreateSchoolInput!): SchoolDTO` - Creates a new school.
    *   `updateSchool(id: Int!, updateSchoolInput: UpdateSchoolInput!): SchoolDTO` - Updates an existing school.
    *   `deleteSchool(id: Int!): Boolean` - Deletes a school.

**Student Access Module:**

*   **Mutations:**
    *   `registerEntry(studentId: Int!): StudentAccessDTO` - Registers student entry.
    *   `registerExit(studentId: Int!): StudentAccessDTO` - Registers student exit.

**Explore the GraphQL Schema in the Playground for detailed input types and response structures.**

## Running with Docker Compose

To run both the BFF and a backend service (assuming you have a backend Dockerfile in a sibling directory named `backend`), you can use Docker Compose.

1.  **Navigate to the root directory** of your project structure (assuming your `docker-compose.yml` is placed one level up from `bff-controle-escolar`). If your `docker-compose.yml` is in `bff-controle-escolar`, then navigate to this directory.

2.  **Run Docker Compose:**

    ```bash
    docker compose up --build
    ```

    This command will build and start both the `backend` and `bff` services as defined in the `docker-compose.yml` file.

    *   The **Backend service** will be accessible at `http://localhost:8080`.
    *   The **BFF service** (GraphQL Playground) will be accessible at `http://localhost:8081/graphql`.

    **Note:**  The provided `docker-compose.yml` in the problem description assumes a sibling directory named `backend` containing a Dockerfile for the backend service.  You need to ensure that the backend service Dockerfile and application are correctly set up in the `backend` directory.

## Running Tests

To execute the unit tests for the application, use the following command:

```bash
npm run test  # or yarn test