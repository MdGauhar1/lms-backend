# 🎓 LMS Microservices Backend

A Learning Management System (LMS) backend built using Java Spring Boot, Apache Kafka, and Microservices architecture. This project demonstrates real-world event-driven communication between services.

## 🚀 Tech Stack
- Java 17  
- Spring Boot  
- Spring Data JPA (Hibernate)  
- Apache Kafka  
- MySQL  
- REST APIs  
- Maven  
- WSL (for Kafka setup)

## 🧩 Microservices Overview
| Service | Description |
|--------|-------------|
| Enrollment Service | Handles user course enrollment |
| Certificate Service | Generates course completion certificates |
| Common Module | Shared Kafka event classes |
| Kafka | Message broker for async communication |

## 🔁 Kafka Event Flow
1. User enrolls in a course  
2. Enrollment Service publishes `CourseEnrolledEvent`  
3. Kafka topic `course-enrolled` receives the event  
4. Certificate Service consumes the event  
5. Certificate PDF is generated  
6. Certificate details are saved in database  

## 📦 Kafka Event Structure
CourseEnrolledEvent  
- userId  
- courseId  
- enrollmentDate  

## 🗂️ Project Structure
lms-backend/  
├── enrollment-service/  
├── certificate-service/  
├── common/  
│   └── CourseEnrolledEvent.java  
├── pom.xml  
└── README.md  

## ⚙️ How to Run the Project
1. Start Zookeeper  
zookeeper-server-start.bat config/zookeeper.properties  

2. Start Kafka  
kafka-server-start.bat config/server.properties  

3. Create Kafka Topic  
kafka-topics.bat --create --topic course-enrolled --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1  

4. Run each microservice  
mvn spring-boot:run  

## 📄 Certificate Storage
Certificates are generated as PDF files and stored in the `certificates/` folder.  
The folder is automatically created if it does not exist.

## 🧪 Testing
- APIs tested using Postman  
- Kafka events verified using logs  
- Database verified using MySQL  

## 🔐 .gitignore
/target  
/.idea  
/.vscode  
*.log  
*.pdf  
certificates/  

## 🌟 Key Features
- Event-driven microservices using Kafka  
- Clean and scalable architecture  
- Real-world LMS workflow  
- Interview-ready backend project  

## 📌 Future Enhancements
- Authentication & Authorization  
- Email notifications  
- Cloud storage for certificates  
- Docker & Kubernetes  
- Frontend integration  

## 👨‍💻 Author
Gauhar  
Backend Developer  
Java | Spring Boot | Kafka | Microservices | Angular

## ⭐ Final Note
This project showcases practical use of Spring Boot microservices with Kafka and is suitable for real-world applications.
