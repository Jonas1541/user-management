# User Management System

A cloud-native user management system built with **Spring Boot 4.0.2**, **Thymeleaf**, **HTMX**, and **PostgreSQL**.

## Prerequisites

-   **Java 25** (Eclipse Temurin recommended)
-   **Docker** & **Docker Compose**
-   **Git**

## Getting Started

Follow these instructions to get the project up and running on your local machine.

### 1. Clone the Repository

```bash
git clone https://github.com/jonasdurau/usermanagement.git
cd usermanagement
```

### 2. Database Setup

We use **Docker Compose** to spin up a PostgreSQL database and pgAdmin container.

```bash
docker compose up -d
```

This will start:
-   **PostgreSQL**: Port `5432`
-   **pgAdmin**: Port `5050` (Login: `admin@admin.com` / `admin`)
-   **Database**: `usermanagement`
-   **User/Pass**: `jonas` / `password123`

### 3. Run the Application

You can run the application directly using the Gradle Wrapper:

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

**Note**: The database migrations (Flyway) will run automatically on startup.

### 4. Docker (Production Mode)

To run the entire stack (Database + Backend) via Docker:

```bash
docker compose up --build
```
This builds the application image using a multi-stage Dockerfile based on `eclipse-temurin:25-jdk`.

## Features

-   **CRUD Operations**: Create, Read, Update, Delete users.
-   **HTMX Integration**: Dynamic search and deletion without full page reloads.
-   **Responsive UI**: Styled with Bootstrap and custom CSS.
-   **Database Migrations**: Managed by Flyway.
