# 📄 Document Classification System

## 🚀 Overview

This project is a Spring Boot backend system that processes documents (PDF or text) and classifies content into predefined topics using keyword-based scoring logic.

---

## 🧠 Features

* Topic Management API
* Document Upload (Text + PDF)
* Text Extraction (PDFBox)
* Sentence-level Classification
* Advanced Scoring Logic
* UNCLASSIFIED handling
* Dashboard Analytics
* Swagger API Documentation
* Dockerized Deployment
* Pagination

---

## ⚙️ Tech Stack

* Java 21
* Spring Boot 4.x
* PostgreSQL (Supabase)
* JPA / Hibernate
* Apache PDFBox
* Swagger (OpenAPI)
* Docker

---

## 📡 APIs

### 1. Add Topic

POST `/api/topics`

### 2. Upload Document

POST `/api/documents`

### 3. Get Results

GET `/api/documents/{id}/results`

### 4. Dashboard

GET `/api/dashboard`

---

## 🧪 Sample Request

```json
{
  "title": "Delhi Bomb Blast",
  "keywords": ["Delhi", "blast", "explosion"]
}
```

---

## 🐳 Run with Docker

```bash
cd folder name
mvn clean package
docker build -t document-classifier .
docker run -p 8080:8080 document-classifier
```

---

## 🔥 Future Improvements

* NLP-based classification
* Multi-language support
* Kafka async processing
* Fuzzy matching

---

## 👨‍💻 Author

Sagar Satyarthi Mishra
