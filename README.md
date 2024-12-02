# Phonebook Application

This project is a **Phonebook Application** built using **Java 17**, **Spring Boot 3**, **Vaadin**, **MongoDB**, and **Docker**. It allows users to manage contacts, with features to add, edit, delete, view, and export contact details as a PDF. MongoDB is used as the database, and mongo-express is included for easy database management.

---

## Features

- **CRUD Operations**: Create, read, update, and delete contacts.
- **PDF Export**: Generate and download a PDF file of all contacts.
- **Dockerized Database**: MongoDB and mongo-express configured via Docker Compose.
- **Interactive UI**: User-friendly interface built with Vaadin.
- **REST API**: Endpoints for exporting data and managing contacts.

---

## Prerequisites

Before running the application, ensure the following are installed:

- **Java 17+**
- **Gradle** (or use the wrapper `./gradlew`)
- **Docker** and **Docker Compose**

---

## Getting Started

## Start the Database Services

To start MongoDB and mongo-express, use Docker Compose:

```bash
docker-compose up -d
```
- Start mongo-express on http://localhost:8081 for database management.
- If you could not authenticate to Mongo Express using credentials in *application.properties*, use basic credentials: **"admin:pass"**

## Run the Application


Launch the Spring Boot application:
```bash
./gradlew bootRun
```

The application will start on http://localhost:8085

