# VaultIQ Banking Portal Client

VaultIQ Banking Portal Client is a modern, responsive Angular single-page application engineered with **Angular 18**, **Tailwind CSS**, and **Chart.js** that connects seamlessly to the **VaultIQ Banking API**.

---

## Features

- **User Onboarding & Authentication**:
  - Secure registration with country-code mobile formatting.
  - Multi-factor OTP verification for secure login and device authorization.
  - JWT stateless session handling with automatic HTTP request interceptor.
  - Password recovery workflow.
- **Account Operations**:
  - Account PIN creation and verification modal for transactional security.
  - Instant cash deposit and cash withdrawal.
  - Real-time intra-bank fund transfer with recipient account validation.
- **Interactive Financial Dashboard**:
  - Live account balance overview with copy-to-clipboard account numbers.
  - Daily debit and credit transaction breakdown (interactive pie chart).
  - Monthly financial trajectory and transaction trend lines.
  - Full transaction history with filterable columns and Excel statement export (`xlsx`).
- **Responsive Layout**:
  - Collapsible sidebar navigation, mobile-responsive layout, and customizable theme components.

---

## Tech Stack

- **Framework**: Angular 18 (standalone components & modular routing)
- **Styling**: Tailwind CSS, FontAwesome 5
- **Charts & Data Visualization**: Chart.js 4, ng2-charts 6
- **Data Export**: SheetJS (xlsx)
- **Internationalization**: ngx-countries-dropdown, libphonenumber-js

---

## Getting Started

### Prerequisites

- Node.js 18+ (tested with Node 20 / 22 / 26)
- npm 9+

### 1. Install Dependencies

```bash
npm install --allow-remote=all
```

### 2. Configure Environment

The API endpoint is configured in `src/environment/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8180/api',
  tokenName: "authToken",
  origin: 'http://localhost:4200' 
};
```

Ensure your VaultIQ backend API is running on `http://localhost:8180`.

### 3. Development Server

Start the Angular development server:

```bash
npm start
```

Navigate to `http://localhost:4200/` in your browser.

### 4. Build for Production

```bash
npm run build
```

The production assets will be output to `dist/vaultiq/`.

---

## License

This project is licensed under the MIT License.
