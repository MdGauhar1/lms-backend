🎓 LMS Microservices Backend

A Learning Management System (LMS) backend built using Java Spring Boot, Kafka, and Microservices architecture.
This system handles course enrollment, event-driven communication, and automatic certificate generation.

🚀 Tech Stack

Java 17

Spring Boot

Spring Data JPA (Hibernate)

Apache Kafka

MySQL

REST APIs

WSL / Docker

Maven

🧩 Microservices Overview
Service	Description
Enrollment Service	Handles course enrollment
Certificate Service	Generates certificates after enrollment
Common Module	Shared event DTOs
Kafka	Event-driven communication
🔁 Event Flow (Kafka)

User enrolls in a course

Enrollment Service publishes CourseEnrolledEvent

Kafka topic: course-enrolled

Certificate Service consumes the event

Certificate PDF is generated and stored

Certificate metadata is saved in database

📦 Kafka Event Example
CourseEnrolledEvent {
    userId,
    courseId,
    enrollmentDate
}

🗂️ Project Structure
lms-backend/
│
├── enrollment-service/
├── certificate-service/
├── common/
│   └── CourseEnrolledEvent.java
├── pom.xml
└── README.md

⚙️ How to Run the Project
1️⃣ Start Kafka & Zookeeper
zookeeper-server-start.bat config/zookeeper.properties
kafka-server-start.bat config/server.properties

2️⃣ Create Kafka Topic
kafka-topics.bat --create ^
--topic course-enrolled ^
--bootstrap-server localhost:9092 ^
--partitions 1 ^
--replication-factor 1

3️⃣ Run Services
mvn spring-boot:run


Run each microservice separately.

📄 Certificate Storage

Certificates are generated as PDF files and stored inside:

/certificates/


Folder is auto-created by the service.

🧪 Testing

APIs tested using Postman

Kafka events verified using logs

Database verified via MySQL queries

🔐 .gitignore (Important)
/target
/.idea
/.vscode
*.log
*.pdf
certificates/

🌟 Key Features

Event-driven architecture using Kafka

Clean separation of microservices

Scalable and loosely coupled design

Real-world LMS workflow

Production-ready folder structure

📌 Future Enhancements

Authentication & Authorization

Email notifications

Cloud storage for certificates

Docker & Kubernetes deployment

Frontend integration (Angular / React)

👨‍💻 Author

Gauhar
Backend Developer | Java | Spring Boot | Kafka

⭐ Final Note

This project demonstrates real-world microservices, Kafka usage, and clean backend design.
