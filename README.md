# Personal Finance & Expense Management System

A Spring Boot REST API backend for tracking personal income and expenses, with JWT-based authentication.

## Tech Stack
- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT (JJWT)
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Lombok

## Features
- User registration & login with JWT authentication
- BCrypt password hashing (no plain-text passwords stored)
- Add / view / update / delete Income records
- Add / view / update / delete Expense records
- Category management (Income & Expense categories, seeded by default)
- Monthly dashboard summary: total income, total expense, balance, expense-by-category breakdown
- Each user can only see/modify their own data (ownership checks on every record)
- Global exception handling with clean JSON error responses
- Input validation (e.g. required fields, valid email, password length)

## Project Structure
```
src/main/java/com/financeapp/expensetracker/
├── config/          # Security config, CORS, data seeder
├── controller/       # REST API endpoints
├── dto/              # Request/response objects
├── entity/           # JPA entities (User, Income, Expense, Category)
├── exception/        # Custom exceptions + global handler
├── repository/        # Spring Data JPA repositories
├── security/          # JWT util, filter, UserDetailsService
└── service/            # Business logic
```

## Setup Instructions

### 1. Prerequisites
- Java 17+ installed
- Maven installed (or use the included Maven wrapper if you add one)
- MySQL Server running locally

### 2. Create the database
You don't need to manually create it — `application.properties` is set to auto-create the database (`createDatabaseIfNotExist=true`). Just make sure MySQL is running.

### 3. Configure database credentials
Open `src/main/resources/application.properties` and update:
```
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### 4. Change the JWT secret (important before deploying anywhere public)
```
jwt.secret=ChangeThisToALongRandomSecretKeyAtLeast32CharsLongForHS256
```

### 5. Build and run
```bash
mvn clean install
mvn spring-boot:run
```
The app will start on `http://localhost:8080`

On first run, default categories are auto-seeded (Salary, Freelance, Food, Travel, Rent, etc.)

## API Endpoints

### Auth (public, no token required)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login, returns JWT token |

**Register request body:**
```json
{
  "username": "amirtha",
  "email": "amirtha@example.com",
  "password": "password123"
}
```

**Login request body:**
```json
{
  "username": "amirtha",
  "password": "password123"
}
```

**Response (both):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "amirtha",
  "role": "USER"
}
```

For all endpoints below, add this header using the token from login/register:
```
Authorization: Bearer <token>
```

### Categories
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/categories` | List all categories |
| POST | `/api/categories` | Create a new category |
| DELETE | `/api/categories/{id}` | Delete a category |

### Income
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/incomes` | Add a new income entry |
| GET | `/api/incomes` | Get all your income entries |
| PUT | `/api/incomes/{id}` | Update an income entry |
| DELETE | `/api/incomes/{id}` | Delete an income entry |

**Add income request body:**
```json
{
  "amount": 50000,
  "description": "June Salary",
  "date": "2026-06-01",
  "categoryId": 1
}
```

### Expenses
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/expenses` | Add a new expense entry |
| GET | `/api/expenses` | Get all your expense entries |
| PUT | `/api/expenses/{id}` | Update an expense entry |
| DELETE | `/api/expenses/{id}` | Delete an expense entry |

**Add expense request body:**
```json
{
  "amount": 1200,
  "description": "Groceries",
  "date": "2026-06-15",
  "categoryId": 4
}
```

### Dashboard
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/dashboard/summary` | Current month's summary (totals + breakdown) |
| GET | `/api/dashboard/summary?year=2026&month=5` | Specific month's summary |

**Response:**
```json
{
  "totalIncome": 50000,
  "totalExpense": 8200,
  "balance": 41800,
  "expenseByCategory": {
    "Food": 3000,
    "Travel": 2200,
    "Rent": 3000
  }
}
```

## Testing the API (Postman)
1. Call `POST /api/auth/register` to create a user → copy the `token` from the response
2. In Postman, go to the **Authorization** tab on every other request → choose **Bearer Token** → paste the token
3. Test adding incomes/expenses, then call `GET /api/dashboard/summary` to see the totals update

## Future Enhancements (v2 ideas)
- Budget planning per category with overspend alerts
- PDF/Excel export of monthly reports
- Admin panel (view all users, manage categories globally)
- Email notifications for budget limits
- React frontend dashboard with charts

## Resume Bullet Points (copy-paste ready)
- Built a secure RESTful Personal Finance Management API using Java, Spring Boot, Spring Security, and JWT authentication
- Designed relational schema with Spring Data JPA and MySQL for Users, Income, Expenses, and Categories with ownership-based access control
- Implemented BCrypt password hashing and stateless JWT-based authentication for secure user sessions
- Built a dashboard reporting endpoint that aggregates monthly income/expense totals and category-wise breakdowns
- Applied global exception handling and request validation for robust, production-style error responses
