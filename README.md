# VaultIQ - Core Banking Platform

VaultIQ is an enterprise-grade full-stack core banking platform engineered for performance, security, and developer ergonomics. It consists of a high-throughput REST API backend built with **Java 25** and **Spring Boot 4.1.2**, coupled with an interactive, responsive single-page client built with **Angular 19** and **Tailwind CSS**.

---

## Architecture Overview

```text
vaultiq/
├── client/              # Frontend: Angular 19 Single-Page Application (Tailwind CSS, Chart.js)
│   ├── src/app/         # Components, services, guards, and interceptors
│   │   ├── components/  # Feature modules (Auth, Dashboard, Accounts, Transactions)
│   │   ├── services/    # REST API client, AuthService, LoaderService
│   │   ├── guards/      # JWT route authentication guards
│   │   └── util/        # Form and validation utilities
│   ├── angular.json     # Angular CLI workspace configuration
│   └── package.json     # Client dependencies and npm scripts
│
├── server/              # Backend: Java 25 & Spring Boot 4.1.2 REST API
│   ├── src/main/java/   # Core banking application source
│   │   └── io/github/deepakjvm/vaultiq/
│   │       ├── config/         # Security, Swagger, Redis, Caffeine, and Password configs
│   │       ├── controller/     # REST controllers (Auth, User, Account, Dashboard)
│   │       ├── dto/            # Data transfer objects and request validations
│   │       ├── entity/         # JPA Entities (User, Account, Transaction, Token, OtpInfo)
│   │       ├── exception/      # Domain-specific banking exception handlers
│   │       ├── mapper/         # Entity-to-DTO mappers
│   │       ├── repository/     # Spring Data JPA repositories
│   │       ├── security/       # JWT filters, entry points, and authentication handlers
│   │       ├── service/        # Banking business logic, transactions, email, and cache
│   │       └── util/           # Security context utilities and validation helpers
│   ├── src/test/java/   # Comprehensive unit and integration test suite
│   ├── docker/          # Docker Compose configurations (PostgreSQL, Redis)
│   ├── pom.xml          # Maven dependencies and build plugins
│   └── VaultIQ Banking API.postman_collection.json  # Preconfigured Postman collection
│
└── README.md            # Unified project documentation
```

---

## Tech Stack Summary

| Layer | Technologies |
|---|---|
| **Frontend** | Angular 19, TypeScript 5.8, Tailwind CSS, Chart.js 4, ng2-charts 8, FontAwesome 5, SheetJS (`xlsx`) |
| **Backend** | Java 25, Spring Boot 4.1.2, Spring Framework 7, Spring Security 7, Spring Data JPA, Hibernate 7 |
| **Data & Cache** | PostgreSQL 17, Redis 7 (distributed caching), Caffeine (in-memory caching), H2 (in-memory test DB) |
| **Security** | Stateless JWT (JJWT 0.12.6), BCrypt PIN & Password Hashing, CORS filtering, role-based access |
| **API Docs & Tools**| OpenAPI 3.0 / Swagger UI (springdoc 2.8.5), Postman Collection |
| **Infrastructure** | Docker, Docker Compose |
| **Testing** | JUnit 5, Mockito, AssertJ, GreenMail JavaMail |

---

## Core Capabilities

- **User Onboarding & Authentication**:
  - Secure user registration with phone validation and country code formatting (`libphonenumber-js`).
  - Multi-factor OTP verification delivered via automated HTML email alerts.
  - Stateless authentication powered by JWT with automatic Angular HTTP interceptor handling.
  - Password recovery workflow backed by cryptographically secure tokens.
- **PIN Security & Account Controls**:
  - 4-digit transaction PIN setup and updates hashed with BCrypt.
  - Modal-based PIN verification prompt prior to executing sensitive transactions.
- **Transactional Banking**:
  - Instant cash deposits and withdrawals with balance verification.
  - Real-time intra-bank fund transfers with transactional atomicity and balance validations.
- **Interactive Financial Dashboard**:
  - Live account balance overview with copy-to-clipboard account numbers.
  - Daily debit and credit transaction breakdown (interactive Chart.js pie chart).
  - Monthly financial trajectory and transaction trend lines.
  - Full transaction history with filterable columns and Excel statement export (`xlsx`).
- **Multi-Tier Caching & Audit**:
  - Multi-tier caching architecture: Redis for account state and dashboard metrics, Caffeine for fast idempotency tracking.
  - Client IP geolocation resolution via FindIP API and automated security email notifications on new logins.

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 25**
- **Apache Maven 3.9+**
- **Node.js 18+** & **npm 9+**
- **Docker & Docker Compose** (for PostgreSQL and Redis)

---

### 1. Start Infrastructure Services

Use the provided Docker Compose configuration to start PostgreSQL and Redis:

```bash
docker compose -f server/docker/docker-compose.yml up -d
```

This starts:
- **PostgreSQL**: `localhost:5432` (database: `vaultiq_db`, user: `postgres`, password: `password`)
- **Redis**: `localhost:6379`

---

### 2. Configure & Run the Backend API

1. Navigate to the server directory:
   ```bash
   cd server
   ```

2. Copy the sample properties file to create your active configuration:
   ```bash
   cp src/main/resources/application.properties.sample src/main/resources/application.properties
   ```
   *(Update your JWT secret, email credentials, or third-party API keys as needed)*

3. Start the Spring Boot API:
   ```bash
   mvn spring-boot:run
   ```

The backend API will be available at `http://localhost:8180`.

---

### 3. Configure & Run the Frontend Client

1. In a new terminal, navigate to the client directory:
   ```bash
   cd client
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the Angular development server:
   ```bash
   npm start
   ```

The frontend portal will be accessible at `http://localhost:4200`.

---

## API Documentation & Tools

### Interactive Swagger UI

When the backend is running, access the interactive OpenAPI documentation:
- **Swagger UI**: [http://localhost:8180/swagger-ui.html](http://localhost:8180/swagger-ui.html)
- **OpenAPI Schema**: [http://localhost:8180/v3/api-docs](http://localhost:8180/v3/api-docs)

### Postman Collection

A preconfigured Postman collection is included in the server directory:
- [VaultIQ Banking API.postman_collection.json](file:///home/deepak/Projects/vaultiq/server/VaultIQ%20Banking%20API.postman_collection.json)

---

## Running Automated Tests & Builds

### Backend Test Suite

Run the full suite of 150 unit and integration tests (uses in-memory H2 database and GreenMail SMTP server):

```bash
cd server
mvn clean test
```

### Backend Production Packaging

```bash
cd server
mvn package -DskipTests
```
Generates executable JAR: `server/target/vaultiq-0.0.1-SNAPSHOT.jar`.

### Frontend Production Build

```bash
cd client
npm run build
```
Generates optimized production assets in `client/dist/vaultiq/`.

