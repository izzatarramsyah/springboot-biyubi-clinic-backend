# Biyubi Clinic Growth Monitoring App

A backend web application built with **Spring Boot**, designed for **Growth Monitoring** of children aged 0-2 years old, according to **WHO Z Score** guidelines. This project aims to provide healthcare professionals with an efficient way to track and analyze a child's growth.

---

## 📦 Tech Stack

- **Java Spring Boot 8**
- **Maven**
- **PostgreSQL**
- **Apache POI** (for handling Excel files)
- **JasperReports** (for generating reports)

---

## 🔧 Features

- 📊 **Growth Monitoring**: Track and monitor the growth of children from 0-2 years.
- 📈 **Z-Score Calculation**: Calculate Z-scores for weight and height based on WHO guidelines.
- 📝 **Reports**: Generate detailed growth reports using **JasperReports**.
- 📊 **Data Export**: Export data to Excel files using **Apache POI**.
- 🚀 **API**: Expose data and reports through RESTful API endpoints.

---

## 🚀 Getting Started

### Prerequisites
- **Java 8** or higher
- **Maven**
- **PostgreSQL**

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/izzatarramsyah/springboot-biyubi-clinic-backend
    cd biyubi-clinic-growth-monitoring
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

---

