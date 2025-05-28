# Digital Queue App

A backend queue management system build with spring boot. Designed to simulate real-time digital queuing system with event driven communcation and secured access. This project as a sandbox for exploring various aspects of java backend development including kafka, jwt and clean acrchitecture

---

## 📦 Tech Stack

- **Java Spring Boot 17**
- **Spring Data JPA**
- **PostgreSQL**
- **Apache Kafka** 
- **Spring Security + JWT**
- **JUnit**

---

## 🔧 Features

- 🔐 **JWT Authentication** : Secure login to access queue services.
- 🧾 **Queue Joining API** : Users can join a queue using a RESTful endpoint.
- 📣 **Kafka Notification** : Event published when a user joins a queue.
- 🧹 **Clean Code Architecture** : Modular and maintainable package structure.
- ✅ **Unit Testing** : Basic test coverage to ensure reliability.

---

## 🚀 Getting Started

### Prerequisites
- **Java 17** or higher
- **Maven**
- **PostgreSQL**

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/izzatarramsyah/java-digital-queue
    cd java-digital-queue
    ```

2. **Configure the database:**
    - Create a new PostgreSQL database.
    - Update the **`application.properties`** or **`application.yml`** with your database credentials.

3. **Build and run the project:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Access the API:**
    - The application should now be running at `http://localhost:8080`.

    - Login CURL :

        ```bash
        curl --location 'http://localhost:8080/api/users/login' \
        --header 'Content-Type: application/json' \
        --data-raw '{
          "email": "user_baru@example.com",
          "password": "password123",
        }'
        ```

    - Register CURL :

        ```bash
        curl --location 'http://localhost:8080/api/users/register' \
        --header 'Content-Type: application/json' \
        --data-raw '{
          "fullname": "user_baru",
          "email": "user_baru@example.com",
          "password": "password123",
          "role": "USER"
        }'
        ```

    - Join Queue CURL :

        ```bash
        curl --location 'http://localhost:8080/api/queue/{queueId}/join?userId={userId}' \
        --header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyX2JhcnVAZXhhbXBsZS5jb20iLCJpc3MiOiJUaWNrZXRpblNlcnZpY2UiLCJpYXQiOjE3NDg0MTgxNzksImV4cCI6MTc0ODQyMTc3OX0.VF44kRt8cQ8Qak3twHlOUAhWIuXftiQOdUv0Gb3639M' \
        --header 'Content-Type: application/json'
        ```

    - Get Queue Today CURL :

        ```bash
        curl --location 'http://localhost:8080/api/queue/today'
        ```

    - Get Queue Entries CURL :

        ```bash
        curl --location 'http://localhost:8080/api/queue/{queueId}/entries'
        ```

    - Call Next Queue CURL :

        ```bash
        curl --location --request POST 'http://localhost:8080/api/queue/1/next'
        ```

---
