# ⚡ Flash Sale Engine – Rate Limited High-Concurrency System

A **Spring Boot–based Flash Sale Engine** designed to handle **high traffic and limited stock purchases** with proper **rate limiting, concurrency control, and scalability**.

This project simulates real-world flash sale scenarios like **Amazon / Flipkart / Myntra** sales, where thousands of users attempt to purchase limited items at the same time.

---

## 🚀 Features

- 🔒 **User-based Rate Limiting** to prevent abuse and bot attacks
- 🛒 **Atomic Product Purchase Logic**
- ⚙️ **High Concurrency Handling**
- 🧠 **Redis-based Optimization**
- 📦 **Apache Kafka for Asynchronous Processing**
- 🔐 **Spring Security Integration**
- 🧪 **RESTful APIs**
- 🐳 **Docker-ready setup**
- 📊 **Clean layered architecture**

---

## 🛠️ Tech Stack

| Category | Technology |
|--------|------------|
| Language | Java |
| Framework | Spring Boot |
| Security | Spring Security |
| Database | MySQL |
| Cache | Redis |
| Messaging | Apache Kafka |
| ORM | JPA / Hibernate |
| Build Tool | Maven |
| Containerization | Docker |
| Logging | Logback |

---

## 🏗️ System Design Highlights

### 🔒 Rate Limiting
- Limits excessive purchase attempts per user
- Protects the system from brute-force and bot traffic
- Returns `429 TOO_MANY_REQUESTS` when limit is exceeded

### ⚙️ Concurrency Control
- Prevents overselling of products
- Ensures atomic stock decrement logic

### 📦 Kafka Integration
- Decouples purchase requests from processing
- Improves scalability and performance

---

## 📂 Project Structure

FlashSaleEngine
├── controller
├── service
├── repository
├── model
├── security
├── config
├── kafka
├── redis
└── exception


---

## 🔗 API Endpoints

### 🛒 Buy Product
```http
POST /buy/{productId}
📌 Possible Responses
200 OK – Purchase successful

429 TOO MANY REQUESTS – Rate limit exceeded

400 BAD REQUEST – Out of stock or invalid request

🔐 Sample Rate Limiting Logic
if (!rateLimitingService.tryAccess(user.getId())) {
    return ResponseEntity
            .status(HttpStatus.TOO_MANY_REQUESTS)
            .body("Too many attempts, try again later!");
}
🐳 Docker Setup
docker build -t flash-sale-engine .
docker run -p 8080:8080 flash-sale-engine
🧪 Run Locally
Prerequisites
Java 21

Maven

Redis

Apache Kafka

MySQL

Steps
git clone https://github.com/Ash8389/FlashSaleEngine.git
cd FlashSaleEngine
mvn clean install
mvn spring-boot:run
📈 Future Enhancements
✅ Distributed Locking using Redisson

📊 Real-time monitoring and metrics

🔁 Retry mechanism with Dead Letter Queue (DLQ)

🌐 Kubernetes deployment

🧪 Load testing using JMeter

👨‍💻 Author
Ashish Kumar Jha
Backend Developer | Java | Spring Boot | Redis | Kafka

🔗 GitHub: https://github.com/Ash8389
