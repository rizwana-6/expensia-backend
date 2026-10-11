# Expensia Backend

REST API for the Expensia expense tracker, built with Spring Boot and MySQL.

## Features
- User register and login
- Transactions API: create, read, update and delete (per user)

## Tech
- Java, Spring Boot
- Spring Data JPA
- MySQL

## How to run
1. Install Java and MySQL.
2. Create the database in MySQL:
   CREATE DATABASE expensia_db;
3. Set your MySQL password (Windows cmd):
   set DB_PASSWORD=your_mysql_password
4. Start the app:
   mvnw spring-boot:run
5. The API runs at http://localhost:9090

## Main endpoints
- POST /api/users/register
- POST /api/users/login
- GET /api/transactions?userId=1
- POST /api/transactions?userId=1
- PUT /api/transactions/{id}
- DELETE /api/transactions/{id}

## Frontend
Frontend repo: https://github.com/rizwana-6/expensia-frontend
It runs at http://localhost:5173 and calls this API.
