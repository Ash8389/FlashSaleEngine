# ⚡ Flash Sale Engine – High-Concurrency, Rate-Limited Purchase System

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-Messaging-black)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A **production-ready Flash Sale Engine** built with **Spring Boot**, designed to safely handle **massive concurrent traffic and limited-stock purchases**.

This project simulates real-world flash sale systems used by **Amazon, Flipkart, Myntra**, where **thousands of users attempt to buy the same product at the same time**—without overselling, crashing, or being abused by bots.

---

## 🚀 Key Features

* 🛡️ **User-based Rate Limiting** (anti-bot & abuse protection)
* 🛒 **Atomic Purchase Flow** (no overselling)
* ⚙️ **High-Concurrency Safe Design**
* 🚀 **Redis-powered performance optimization**
* 📦 **Kafka-based asynchronous order processing**
* 🔐 **JWT-secured APIs with Spring Security**
* 🧪 **RESTful API architecture**
* 🐳 **Docker-ready deployment**
* 📊 **Clean & scalable layered architecture**

---

## 🛠️ Tech Stack

| Layer            | Technology            |
| ---------------- | --------------------- |
| Language         | Java 21               |
| Framework        | Spring Boot           |
| Security         | Spring Security + JWT |
| Database         | MySQL                 |
| Cache            | Redis                 |
| Messaging        | Apache Kafka          |
| ORM              | JPA / Hibernate       |
| Build Tool       | Maven                 |
| Containerization | Docker                |
| Logging          | Logback               |

---

## 🏗️ System Design Overview

### 🔒 Rate Limiting

* Applied **per user** to restrict excessive purchase attempts
* Protects against **DDoS, brute-force, and bot traffic**
* Implemented using **Redis counters** for low-latency checks
* Exceeds limit → returns `429 TOO_MANY_REQUESTS`

---

### ⚙️ Concurrency Control

* Guarantees **atomic stock decrement**
* Prevents **race conditions and overselling**
* Uses transactional boundaries at service/database level
* Designed to stay consistent under **thousands of parallel requests**

---

### 📦 Kafka – Asynchronous Order Processing

* Decouples **API layer** from **order processing**
* Improves throughput, scalability, and fault tolerance
* Enables future extensions like:

  * Retry queues
  * Dead Letter Queue (DLQ)
  * Event-driven analytics

---

## 📂 Project Structure

```
FlashSaleEngine
├── controller      # REST Controllers
├── service         # Business Logic
├── repository      # Database Access Layer
├── model           # Entities & DTOs
├── security        # JWT & Security Config
├── config          # Application Configurations
├── kafka           # Kafka Producer & Consumer
├── redis           # Cache & Rate Limiting Logic
└── exception       # Global Exception Handling
```

---

## 🔗 API Endpoints

### 🛒 Buy Product

```http
POST /buy/{productId}
```

#### Responses

| Status                  | Description                    |
| ----------------------- | ------------------------------ |
| `200 OK`                | Purchase successful            |
| `429 TOO_MANY_REQUESTS` | Rate limit exceeded            |
| `400 BAD_REQUEST`       | Out of stock / invalid request |

---

### 🔐 Sample Rate Limiting Logic

```java
if (!rateLimitingService.tryAccess(user.getId())) {
    return ResponseEntity
        .status(HttpStatus.TOO_MANY_REQUESTS)
        .body("Too many attempts, try again later!");
}
```

---

## 🐳 Docker Setup

```bash
docker build -t flash-sale-engine .
docker run -p 8080:8080 flash-sale-engine
```

---

## 🧪 Run Locally

### Prerequisites

* Java 21
* Maven
* MySQL
* Redis
* Apache Kafka

### Steps

```bash
git clone https://github.com/Ash8389/FlashSaleEngine.git
cd FlashSaleEngine
mvn clean install
mvn spring-boot:run
```

---

## 🔥 Load Testing (Apache JMeter)

Simulate **real flash sale traffic** using JMeter.

### 🎯 Goals

* Validate rate limiting
* Ensure zero overselling
* Measure response time under load
* Verify system stability

---

### 🧰 Test Plan

* **Threads:** 1000+
* **Ramp-up:** 10–30 seconds
* **Loop Count:** 1

**Request**

* `POST /buy/{productId}`
* Headers:

  * `Authorization: Bearer <JWT_TOKEN>`
  * `Content-Type: application/json`

---

### ▶️ Run Test

```bash
jmeter -n -t flash-sale-test.jmx -l results.jtl
```

---

## 📊 Expected Results

* ✔️ Successful purchases (`200 OK`)
* ⛔ Excess traffic blocked (`429 TOO_MANY_REQUESTS`)
* 🔒 No negative stock values
* ⚡ Stable response times

---

## 🚀 Future Enhancements

* Distributed locking with **Redisson**
* Monitoring with **Prometheus & Grafana**
* Retry mechanism + **DLQ**
* **Kubernetes deployment**
* Advanced stress & chaos testing

---

## 👨‍💻 Author

**Ashish Kumar Jha**
Backend Developer | Java | Spring Boot | Redis | Kafka

🔗 GitHub: [https://github.com/Ash8389](https://github.com/Ash8389)

---

⭐ If you find this project useful, consider starring the repository!
