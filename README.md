# VaultIQ - Core Banking Platform

VaultIQ is an enterprise-grade full-stack core banking platform engineered for performance, security, and developer ergonomics. The platform consists of a high-throughput REST API backend built on **Spring Boot 4.1.2** and **Java 25**, coupled with an interactive, responsive single-page client built on **Angular 18** and **Tailwind CSS**.

---

## Architecture Overview

```text
vaultiq/
├── client/              # Frontend: Angular 18 Single-Page Application (Tailwind CSS, Chart.js)
│   ├── src/app/         # Components, services, guards, and interceptors
│   ├── angular.json     # Angular CLI workspace configuration
│   └── package.json     # Client dependencies and npm scripts
│
├── server/              # Backend: Java 25 & Spring Boot 4.1.2 REST API
│   ├── src/             # Spring Boot source code and test suites
│   ├── docker/          # Docker Compose configurations (PostgreSQL, Redis)
│   ├── pom.xml          # Maven dependencies and build plugins
│   └── README.md        # Detailed backend API documentation
│
└── README.md            # Root monorepo overview
```

---

## Tech Stack Summary

| Layer | Technologies |
|---|---|
| **Frontend** | Angular 18, TypeScript 5.4, Tailwind CSS, Chart.js 4, FontAwesome 5, SheetJS (xlsx) |
| **Backend** | Java 25, Spring Boot 4.1.2, Spring Security 7, Spring Data JPA, Hibernate 7 |
| **Data & Cache** | PostgreSQL 17, Redis 7 (distributed caching), Caffeine (in-memory caching), H2 (testing) |
| **Security** | JWT (JJWT 0.12.6), BCrypt PIN & Password Hashing, CORS filtering, stateless auth |
| **API Docs & Tools** | OpenAPI 3.0 / Swagger UI (springdoc 2.8.5), Postman Collection |
| **Infrastructure** | Docker, Docker Compose |

---

## Core Capabilities

- **User Onboarding & Authentication**:
  - Secure registration with phone validation and country codes.
  - Multi-factor OTP verification via automated email notifications.
  - Stateless JWT authentication with seamless token renewal.
- **PIN Security & Account Controls**:
  - 4-digit transaction PIN creation and update hashed with BCrypt.
  - Password recovery workflow via secure reset tokens.
- **Transactional Banking**:
  - Cash deposits and withdrawals with balance checks and idempotency guards.
  - Real-time intra-bank fund transfers with transactional atomicity.
- **Analytics & Dashboard**:
  - Live account balance overview and debit/credit aggregations.
  - Daily pie charts and monthly financial trajectory line charts.
  - Transaction history table with Excel statement export.

---

## Quick Start

### 1. Start Infrastructure (PostgreSQL & Redis)

```bash
docker compose -f server/docker/docker-compose.yml up -d
```

### 2. Run the Backend API

```bash
cd server
cp src/main/resources/application.properties.sample src/main/resources/application.properties
mvn spring-boot:run
```

API runs at: `http://localhost:8180`  
Swagger UI: `http://localhost:8180/swagger-ui.html`

### 3. Run the Frontend Client

```bash
cd client
npm install --allow-remote=all
npm start
```

Client runs at: `http://localhost:4200`

---

## Testing

- **Backend Tests**: Run `mvn clean test` from `server/` (150 unit and integration tests).
- **Frontend Build**: Run `npm run build` from `client/` (generates production bundles into `dist/vaultiq/`).

---

## License

This project is licensed under the MIT License.
