# Medical Appointment Application

A comprehensive web-based medical appointment scheduling system built with Spring Boot, Thymeleaf, and MySQL. This application allows healthcare facilities to manage patients and appointments efficiently through an intuitive web interface with persistent database storage.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Project Evolution](#project-evolution)
- [Security Features](#security-features)
- [Potential Enhancements](#potential-enhancements)
- [Author](#author)

## Overview

The Medical Appointment Application is designed to streamline the process of managing patient information and scheduling appointments in healthcare settings. Built as a capstone project to demonstrate proficiency in Java, Spring Boot, web development, database integration, and software testing practices.

### Project Context

This project represents the culmination of multiple enhancements for my Computer Science Capstone:

- **Original Project (CS-320):** Basic command-line service with in-memory storage and JUnit testing
- **Enhancement #1:** Transformed into a full-featured web application with Spring Boot and modern UI
- **Enhancement #3:** Integrated MySQL database for persistent storage and enhanced security

## Features

### Patient Management
- Add new patients with comprehensive information validation
- View all patients in a responsive table
- Edit existing patient information
- Delete patients with confirmation prompts
- Unique patient ID generation (PAT1001, PAT1002, etc.)
- **Persistent storage** - patient data survives application restarts

### Appointment Scheduling
- Schedule appointments with date/time validation
- Link appointments to existing patients
- Assign doctors to appointments
- Add appointment descriptions and notes
- Edit and cancel existing appointments
- Unique appointment ID generation (APT2001, APT2002, etc.)
- **Database persistence** - appointments stored permanently

### Data Validation & Security
- Server-side validation for all user inputs
- Email format validation with regex patterns
- Phone number format validation (10 digits only)
- Future date validation for appointments
- Field length restrictions with user feedback
- **SQL injection prevention** through input sanitization
- **Pattern validation** for names and doctor fields
- **Parameterized queries** via JPA

### Database Features
- Full CRUD operations with MySQL
- Automatic timestamp tracking (created_at, updated_at)
- Transaction management for data consistency
- Referential integrity between patients and appointments
- Audit trail capabilities

### User Interface
- Responsive Bootstrap-based design
- Clean, professional medical theme
- Intuitive navigation between sections
- Success/error message notifications
- Confirmation dialogs for destructive actions

## Technology Stack

### Backend
- **Java 21** - Core programming language
- **Spring Boot 3.5.5** - Application framework
- **Spring MVC** - Web framework
- **Spring Data JPA** - Data persistence and ORM
- **Hibernate** - JPA implementation
- **MySQL 9.4.0** - Production database
- **Maven** - Build automation and dependency management

### Frontend
- **Thymeleaf** - Server-side templating engine
- **Bootstrap 5.3.0** - CSS framework
- **Bootstrap Icons 1.11.3** - Icon library
- **HTML5/CSS3** - Markup and styling
- **JavaScript** - Client-side functionality

### Database & Persistence
- **MySQL Connector/J** - JDBC driver for MySQL
- **JPA (Jakarta Persistence API)** - ORM specification
- **Hibernate** - ORM implementation
- **HikariCP** - Connection pooling (built into Spring Boot)

### Testing
- **JUnit 5** - Unit testing framework
- **Spring Boot Test** - Integration testing
- **Mockito** - Mocking framework

### Development Tools
- **Visual Studio Code** - Primary IDE
- **Git** - Version control
- **GitHub** - Repository hosting
- **MySQL Workbench** - Database management

## Architecture

The application follows a **layered architecture** with clear separation of concerns:

### Presentation Layer (View)
- Thymeleaf templates in `src/main/resources/templates/`
- Bootstrap-based responsive design
- Custom CSS for medical application theming
- Client-side validation and user feedback

### Controller Layer
- `HomeController.java` - Landing page routing
- `PatientController.java` - Patient management endpoints (CRUD operations)
- `AppointmentController.java` - Appointment management endpoints (CRUD operations)
- Handles HTTP requests/responses
- Form validation with BindingResult
- Flash attributes for user feedback

### Service Layer (Business Logic)
- `PatientService.java` - Patient business logic, validation, and coordination
- `AppointmentService.java` - Appointment business logic and validation
- Transaction management with `@Transactional`
- Business rule enforcement
- ID generation logic

### Repository Layer (Data Access)
- `PatientRepository.java` - JPA repository for Patient entity
- `AppointmentRepository.java` - JPA repository for Appointment entity
- Custom query methods using Spring Data JPA naming conventions
- Automatic CRUD method implementation

### Model Layer (Domain Entities)
- `Patient.java` - Patient entity with JPA annotations
- `Appointment.java` - Appointment entity with validation rules
- Bean Validation annotations (`@NotBlank`, `@Pattern`, `@Email`)
- JPA mapping annotations (`@Entity`, `@Table`, `@Column`)

### Database Layer
- MySQL database with two main tables:
  - `patients` - Patient information storage
  - `appointments` - Appointment records
- Automatic schema generation via Hibernate
- Indexed columns for query performance

### Architecture Diagram
```
┌─────────────────────────────────────┐
│      Presentation Layer             │
│   (Thymeleaf Templates + HTML)     │
└─────────────────────────────────────┘
                 ↕ HTTP
┌─────────────────────────────────────┐
│       Controller Layer              │
│  (Handle requests, validation)     │
└─────────────────────────────────────┘
                 ↕
┌─────────────────────────────────────┐
│        Service Layer                │
│  (Business logic, transactions)    │
└─────────────────────────────────────┘
                 ↕
┌─────────────────────────────────────┐
│      Repository Layer               │
│     (Spring Data JPA)              │
└─────────────────────────────────────┘
                 ↕ JDBC
┌─────────────────────────────────────┐
│       Database Layer                │
│      (MySQL Database)              │
└─────────────────────────────────────┘
```

### Key Design Patterns
- **Dependency Injection** - Spring's IoC container manages component relationships
- **Repository Pattern** - Spring Data JPA provides data access abstraction
- **Transaction Management** - Declarative transactions with `@Transactional`
- **MVC Pattern** - Separation of concerns across presentation, business, and data layers
- **ORM (Object-Relational Mapping)** - JPA/Hibernate maps Java objects to database tables

## Installation

### Prerequisites
- **Java 21 or higher**
- **Maven 3.6+** (or use included Maven wrapper)
- **MySQL 8.0+** (or MySQL 9.x)
- **Git**

### Setup Instructions

#### 1. Install MySQL

**Windows:**
- Download MySQL Community Server from https://dev.mysql.com/downloads/mysql/
- Run the installer and complete setup

**Linux:**
```bash
sudo apt-get update
sudo apt-get install mysql-server
sudo mysql_secure_installation
```

#### 2. Create Database

Open MySQL command line or MySQL Workbench and run:

```sql
CREATE DATABASE medical_appointment_db;
CREATE USER 'medapp_user'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON medical_appointment_db.* TO 'medapp_user'@'localhost';
FLUSH PRIVILEGES;
```

#### 3. Clone the Repository

```bash
git clone https://github.com/sumbernotas/MedicalAptApplication.git
cd medical-appointment-app
```

#### 4. Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.application.name=medical-appointment-app

# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/medical_appointment_db
spring.datasource.username=medapp_user
spring.datasource.password=your_secure_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Additional settings
spring.jpa.open-in-view=false
```

**Important:** Replace `your_secure_password` with your actual database password.

#### 5. Build the Application

```bash
./mvnw clean install
```

#### 6. Run the Application

```bash
./mvnw spring-boot:run
```

#### 7. Access the Application

- Open your web browser
- Navigate to `http://localhost:8080`
- The database tables will be created automatically on first run

### Verifying Installation

Check that tables were created:

```sql
USE medical_appointment_db;
SHOW TABLES;
DESCRIBE patients;
DESCRIBE appointments;
```

You should see both tables with their respective columns.

## Usage

### Getting Started

1. **Homepage** - Navigate to `http://localhost:8080` for the main dashboard
2. **Add Patients** - Click "Manage Patients" → "Add New Patient"
3. **Schedule Appointments** - Click "Manage Appointments" → "Add New Appointment"
4. **View Records** - Use the list views to see all patients and appointments

### Patient Management

**Adding a Patient:**
1. Click "Add New Patient"
2. Fill in required information:
   - **Name:** 1-25 characters, letters only (with spaces, hyphens, apostrophes)
   - **Phone:** Exactly 10 digits
   - **Email:** Valid email format
3. Click "Add Patient"
4. Patient is saved to database with unique ID (e.g., PAT1001)

**Editing a Patient:**
1. Find the patient in the list
2. Click "Edit" next to their record
3. Modify the information
4. Click "Update Patient"
5. Changes are persisted to database

**Deleting a Patient:**
1. Click "Delete" next to the patient record
2. Confirm deletion in the dialog
3. Patient is permanently removed from database

### Appointment Scheduling

**Creating an Appointment:**
1. Click "Add New Appointment"
2. Select a patient from the dropdown (must exist first)
3. Enter doctor's name (1-25 characters, letters/periods only)
4. Choose a **future date** (past dates rejected)
5. Add description (1-40 characters)
6. Click "Save Appointment"
7. Appointment saved to database with unique ID (e.g., APT2001)

**Managing Appointments:**
- Edit appointments to change details
- Delete appointments when cancelled
- View all appointments with patient names displayed

## Database Schema

### Tables

#### `patients` Table
```sql
CREATE TABLE patients (
    patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(25) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);
```

**Columns:**
- `patient_id` - Auto-incremented primary key
- `patient_code` - Business identifier (PAT1001, PAT1002...)
- `name` - Patient's full name
- `phone` - 10-digit phone number
- `email` - Patient's email address
- `created_at` - Record creation timestamp
- `updated_at` - Last modification timestamp

#### `appointments` Table
```sql
CREATE TABLE appointments (
    appointment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_code VARCHAR(20) UNIQUE NOT NULL,
    patient_code VARCHAR(20) NOT NULL,
    doctor_name VARCHAR(25) NOT NULL,
    appointment_date DATE NOT NULL,
    description VARCHAR(40) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);
```

**Columns:**
- `appointment_id` - Auto-incremented primary key
- `appointment_code` - Business identifier (APT2001, APT2002...)
- `patient_code` - Links to patient (foreign key relationship)
- `doctor_name` - Assigned doctor's name
- `appointment_date` - Scheduled appointment date
- `description` - Appointment notes/reason
- `created_at` - Record creation timestamp
- `updated_at` - Last modification timestamp

### Relationships
- **One-to-Many:** One Patient can have multiple Appointments
- **Referential Integrity:** Application enforces that appointments reference existing patients
- **Cascade Behavior:** Managed at application level (optional deletion of patient appointments)

### Indexes
- Primary keys are automatically indexed
- Unique constraints on `patient_code` and `appointment_code`
- Consider adding indexes on `appointment_date` for query performance

## Testing

The application includes comprehensive test coverage using JUnit 5.

### Running Tests

```bash
./mvnw test
```

### Test Structure

**Service Layer Tests:**
- `PatientServiceTest.java` - Unit tests for patient operations
- `AppointmentServiceTest.java` - Unit tests for appointment operations

### Test Coverage Areas

**Patient Service Tests:**
- Adding valid/invalid patients
- ID generation and uniqueness
- Validation scenarios (name length, phone format, email format)
- CRUD operations (create, read, update, delete)
- Pattern validation for security
- Data persistence verification

**Appointment Service Tests:**
- Creating appointments with validation
- Patient-appointment relationships
- Date validation (future dates only)
- Doctor name validation with patterns
- Description length constraints
- Database transaction testing

### Integration Testing
- Tests verify interaction between service and repository layers
- Database operations tested with in-memory H2 for unit tests
- Full MySQL integration tested manually

## Project Evolution

### Original Project (CS-320 - Software Testing & QA)
**Features:**
- Command-line interface
- Basic CRUD operations
- In-memory HashMap storage
- JUnit testing framework
- Service-oriented architecture

**Limitations:**
- No user interface
- Data lost on application restart
- Limited scalability
- Basic validation only

### Enhancement #1: Software Design and Engineering
**Improvements:**
- Full web application with Spring Boot
- Thymeleaf templating engine
- Bootstrap responsive UI
- MVC architectural pattern
- Enhanced validation and error handling
- Professional user interface
- RESTful-style routing

**Technologies Added:**
- Spring Boot framework
- Spring MVC
- Thymeleaf
- Bootstrap 5
- HTML/CSS/JavaScript

### Enhancement #3: Databases
**Improvements:**
- MySQL database integration
- Spring Data JPA / Hibernate ORM
- Persistent data storage
- Transaction management
- Audit timestamps (created_at, updated_at)
- Repository pattern implementation
- Enhanced security with input sanitization
- SQL injection prevention
- Database connection pooling

**Technologies Added:**
- MySQL database
- Spring Data JPA
- Hibernate ORM
- MySQL Connector/J
- JPA annotations
- Pattern validation

### Key Achievements Across Enhancements

| Aspect | Original | Enhancement #1 | Enhancement #3 |
|--------|----------|----------------|----------------|
| **Interface** | Command-line | Web UI | Web UI |
| **Storage** | HashMap | HashMap | MySQL Database |
| **Persistence** | No | No | Yes |
| **Framework** | Plain Java | Spring Boot | Spring Boot + JPA |
| **Validation** | Basic | Enhanced | Enhanced + Patterns |
| **Security** | Minimal | Input validation | SQL injection prevention |
| **Architecture** | Simple | MVC | Layered + Repository |
| **Scalability** | Limited | Moderate | Production-ready |

## Security Features

### Input Validation
- **Pattern Validation:** Regex patterns prevent malicious input
  ```java
  @Pattern(regexp = "^[a-zA-Z\\s'-]+$")
  private String name;
  ```
- **Length Restrictions:** All fields have maximum length constraints
- **Type Validation:** Email, phone number format validation
- **Business Rules:** Future dates only for appointments

### SQL Injection Prevention
- **Parameterized Queries:** JPA uses prepared statements automatically
- **Input Sanitization:** Pattern validation rejects SQL keywords
- **No Dynamic SQL:** All queries generated by Spring Data JPA

### Data Integrity
- **Transaction Management:** ACID compliance with `@Transactional`
- **Unique Constraints:** Prevent duplicate IDs
- **Referential Integrity:** Application-level enforcement
- **Audit Trail:** Automatic timestamps on all records

### Best Practices Implemented
- Server-side validation (never trust client)
- Parameterized queries (no string concatenation)
- Input pattern matching
- Transaction isolation
- Error handling without exposing internals

## Potential Improvements

**Security & Authentication:**
- User authentication and authorization
- Role-based access control (Admin, Doctor, Receptionist)
- Password encryption with BCrypt
- Session management
- HTTPS/SSL configuration

**Advanced Features:**
- Search and filter functionality
- Appointment reminders via email/SMS
- Calendar view for appointments
- Patient medical history tracking
- Appointment status (confirmed, cancelled, completed)
- Doctor scheduling and availability management

**Technical Improvements:**
- Redis caching for performance
- Database connection pooling optimization
- Soft deletes instead of hard deletes
- Database migration scripts (Flyway/Liquibase)
- RESTful API endpoints
- Pagination for large datasets
- Export functionality (PDF/Excel reports)

**DevOps & Deployment:**
- Docker containerization
- CI/CD pipeline (GitHub Actions)
- Cloud deployment (AWS/Azure)
- Monitoring and logging (ELK stack)
- Automated backups
- Load balancing for high availability

## Development Notes

### Key Learning Outcomes

**Enhancement #1 (Software Design & Engineering):**
- Spring Boot framework mastery
- MVC architectural pattern
- Web development with Thymeleaf
- Bootstrap responsive design
- RESTful routing principles
- Form validation and error handling

**Enhancement #3 (Databases):**
- Database design and schema creation
- ORM concepts with JPA/Hibernate
- Spring Data JPA repository pattern
- Transaction management
- Entity mapping and relationships
- SQL injection prevention techniques
- Database connection configuration
- Audit trail implementation

### Project Management
- Iterative development approach
- Version control with Git
- Documentation as code evolves
- Test-driven considerations
- Code review and refactoring

### Professional Skills Demonstrated
- Problem-solving and debugging
- Research and self-learning
- Technical documentation
- Software architecture design
- Full-stack development capabilities

## Author

**Summer Bernotas**  
Bachelor of Science in Computer Science  
Southern New Hampshire University  

**Capstone Project Components:**
- Enhancement #1: Software Design and Engineering
- Enhancement #3: Databases

---

**Last Updated:** October 2025  
**Version:** 2.0.0  
**Status:** Complete - Capstone Enhancements #1 and #3 Implemented

