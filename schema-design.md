# MySQL Database Design for Smart Clinic

## Tables

### Doctor
- id (PK)
- name
- specialization
- email
- phone

### Patient
- id (PK)
- name
- email
- phone
- dateOfBirth

### Appointment
- id (PK)
- dateTime
- doctor_id (FK)
- patient_id (FK)

### Admin
- id (PK)
- username
- password
