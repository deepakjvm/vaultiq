# VaultIQ Banking API

VaultIQ Banking API is a secure, high-performance core banking REST API built with **Java 25**, **Spring Boot 4.1.2**, and **PostgreSQL**. It delivers robust financial transactions, multi-tier caching (Redis + Caffeine), JWT-based stateless authentication, and comprehensive auditability.

---

## Key Features

- **User Management & Authentication**:
  - Secure registration, profile management, and account activation.
  - Stateless authentication powered by JWT (JJWT 0.12).
  - OTP verification and email notifications for account operations and new device logins.
- **PIN & Security Management**:
  - Secure PIN creation and updates hashed with BCrypt.
  - Password reset flows backed by cryptographically secure tokens.
- **Core Banking Operations**:
  - Cash deposits and withdrawals with balance checks and transaction records.
  - Real-time intra-bank fund transfers with transactional integrity and balance validation.
- **Financial Dashboard & Analytics**:
  - Account summary, balance inquiry, and monthly transaction history.
  - Analytical aggregations for total credits, debits, and account statements.
- **Multi-Tier Caching & Performance**:
  - Distributed caching via **Redis** for dashboards and account state.
  - High-performance local caching via **Caffeine** for fast idempotency token lookups.
- **Audit & Security Alerts**:
  - Client IP geolocation resolution via FindIP API.
  - Automated HTML email notifications for transactions, logins, and OTP verification.

---

## Technology Stack

- **Language**: Java 25
- **Framework**: Spring Boot 4.1.2 (Spring Framework 7)
- **Security**: Spring Security 7, JJWT (io.jsonwebtoken:jjwt-api:0.12.6)
- **Database & ORM**: PostgreSQL 17, Spring Data JPA, Hibernate 7
- **Caching**: Redis (Lettuce), Caffeine Cache
- **Documentation**: OpenAPI 3.0 / Swagger UI (springdoc-openapi 2.8.5)
- **Containerization**: Docker, Docker Compose
- **Testing**: JUnit 5, Mockito, AssertJ, GreenMail, H2 In-Memory DB

---

## Architecture & Project Structure

```text
io.github.deepakjvm.vaultiq
├── config          # Security, Swagger, Redis, Caffeine, and Password configurations
├── controller      # REST endpoints (Auth, User, Account, Dashboard, Exception Handling)
├── dto             # Data transfer objects and validation schemas
├── entity          # JPA Entities (User, Account, Transaction, Token, OtpInfo)
├── exception       # Domain-specific banking exceptions and error responses
├── mapper          # Entity-to-DTO mappers
├── repository      # Spring Data JPA repositories
├── security        # JWT filters, entry points, and authentication handlers
├── service         # Banking business logic, transactions, email, and cache services
├── type            # Cache keys and transaction enumerations
└── util            # Security context utilities, JSON helpers, and validation routines
```

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 25**
- **Apache Maven 3.9+**
- **Docker & Docker Compose** (for running PostgreSQL and Redis)

### 1. Start Infrastructure Services

Use the provided Docker Compose file to launch PostgreSQL and Redis:

```bash
docker compose -f docker/docker-compose.yml up -d
```

This starts:
- **PostgreSQL**: `localhost:5432` (database: `vaultiq_db`, user: `postgres`, password: `password`)
- **Redis**: `localhost:6379`

### 2. Configure Environment

Copy the sample properties file to create your active configuration:

```bash
cp src/main/resources/application.properties.sample src/main/resources/application.properties
```

Update your JWT secret, email credentials, or third-party API keys in `application.properties` as needed.

### 3. Build & Run

Run the application with Maven:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8180`.

---

## API Documentation & Testing

### Swagger UI

Once the application is running, open the interactive OpenAPI Swagger UI:
- **URL**: [http://localhost:8180/swagger-ui.html](http://localhost:8180/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8180/v3/api-docs](http://localhost:8180/v3/api-docs)

### Postman Collection

A preconfigured Postman collection is included in the root directory:
- [VaultIQ Banking API.postman_collection.json](file:///home/deepak/code/BankingPortal-API/VaultIQ%20Banking%20API.postman_collection.json)

---

## Running Automated Tests

Run the full test suite (unit and integration tests using H2 and GreenMail):

```bash
mvn clean test
```

---

## License

This project is licensed under the MIT License.
