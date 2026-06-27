# 🔐 Auth Vault

> A developer-first authentication service built with **Spring Boot** that provides secure JWT authentication, Refresh Tokens, and REST APIs for seamless integration into any application.
(In Progress)
---

## 📌 Overview

Auth Vault is a production-inspired authentication backend designed to eliminate the need to build authentication from scratch for every project.

It demonstrates modern backend development practices including layered architecture, JWT-based authentication, refresh token management, DTOs, validation, centralized exception handling, and Spring Security.

> **Project Status:** 🚧 Under Development

---

## ✨ Features

* User Registration
* User Login
* JWT Access Token Authentication
* Refresh Token Support
* BCrypt Password Hashing
* Protected REST APIs
* Spring Security Integration
* Global Exception Handling
* Request Validation
* DTO-based API Design
* Swagger/OpenAPI Documentation

---

## 🛠 Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT (JSON Web Tokens)
* Hibernate
* MySQL
* Maven

### Tools

* IntelliJ IDEA
* Postman
* Git & GitHub

---

## 🏗 Architecture

```
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

Authentication Flow

```
Login Request
      │
      ▼
Authenticate Credentials
      │
      ▼
Generate JWT + Refresh Token
      │
      ▼
Return Tokens
      │
      ▼
Client Stores Tokens
      │
      ▼
Protected Requests
      │
      ▼
JWT Filter
      │
      ▼
Token Validation
      │
      ▼
Access Granted
```

---

## 📂 Project Structure

```
src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── exception
 ├── repository
 ├── security
 ├── service
 └── util
```

---

## 📡 API Endpoints

### Authentication

| Method | Endpoint       | Description                 |
| ------ | -------------- | --------------------------- |
| POST   | /auth/register | Register a new user         |
| POST   | /auth/login    | Authenticate user           |
| POST   | /auth/refresh  | Generate a new access token |

### Protected

| Method | Endpoint  | Description                             |
| ------ | --------- | --------------------------------------- |
| GET    | /users/me | Retrieve authenticated user information |

---

## 🔒 Security

* BCrypt password hashing
* JWT Access Tokens
* Refresh Token mechanism
* Stateless authentication
* Spring Security Filter Chain
* Protected API endpoints

---

## 🚀 Getting Started

### Clone Repository

```bash
git clone https://github.com/<your-username>/auth-vault.git
```

### Configure Database

Update your `application.properties` with your MySQL credentials.

### Run

```bash
mvn spring-boot:run
```

Open Swagger:

```
http://localhost:8080/swagger-ui.html
```

---

## 📖 Future Improvements

* Email Verification
* Password Reset
* OAuth2 (Google/GitHub)
* API Keys
* Multi-Tenant Support
* Docker Deployment
* Redis Token Store
* Role-Based Authorization (RBAC)

---

## 🎯 Purpose

This project was built to strengthen backend engineering skills and demonstrate the implementation of secure authentication using Spring Boot and JWT while following clean architecture and industry best practices.

---

## 📄 License

This project is intended for educational and portfolio purposes.
