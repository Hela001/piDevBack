# 🏗️ Construction Management Web Application

![Status](https://img.shields.io/badge/status-active-brightgreen)
![Tech](https://img.shields.io/badge/microservices-SpringBoot|Angular-blue)

## 🧾 Overview

This project is a **Construction Management Web Application** developed as part of an academic initiative at *Esprit School of Engineering*. It provides complete management of construction-related operations including:

- Project and team management
- Logistics (materials, vehicles, suppliers)
- Recruitment and HR processes
- Orders and billing management

The application is built using a **microservices architecture** to ensure modularity, scalability, and maintainability.

> #Esprit_school_of_engineering

---

## ⚙️ Tech Stack

### 🖥️ Front-end
- Angular

### 🛠️ Back-end
- Spring Boot (Java)
- Spring Cloud
- OpenFeign
- Eureka Discovery Server
- API Gateway
- Spring Security with Keycloak

### 🗃️ Database
- MySQL (main services)
- MongoDB (common service)

### 📦 DevOps & Tools
- GitHub for source control

---

## 🧱 Architecture

- 7 main microservices:
  - User Service
  - Project Service
  - Equipment Service
  - Vehicle Service
  - Team Service
  - Order & Billing Service
  - Recruitment Service

Each developer is responsible for one microservice. Microservices communicate through **OpenFeign** and are deployed via **Docker Hub**.

A central integration machine handles:
- API Gateway
- Eureka Server
- Configuration Server
- JWT

---

## 🚀 Installation Guide

### Prerequisites

- Java 17
- Node.js & Angular CLI
- MySQL  installed
- Git

