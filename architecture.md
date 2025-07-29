# Smart Clinic Management System—Architecture

## Roles
- Admin
- Doctor
- Patient

## Technologies Used
- Backend: Java + Spring Boot
- Frontend: HTML/CSS/JS
- DB: MySQL (Relational), MongoDB (NoSQL)
- Auth: JWT
- CI/CD: GitHub Actions
- Container: Docker

## Microservices
- User Service
- Appointment Service
- Prescription Service

## DB Design
- Doctors, Patients, Appointments → MySQL
- Prescriptions → MongoDB

## Access Control
- Role-based using Spring Security + JWT
