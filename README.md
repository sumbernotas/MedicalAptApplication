# MedicalAptApplication
# Medical Appointment Application

A comprehensive web-based medical appointment scheduling system built with Spring Boot and Thymeleaf. This application allows healthcare facilities to manage patients and appointments efficiently through an intuitive web interface.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Medical Appointment Application is designed to streamline the process of managing patient information and scheduling appointments in healthcare settings. Built as a capstone project to demonstrate proficiency in Java, Spring Boot, web development, and software testing practices.

### Project Context

This project serves as Enhancement #1 for my Computer Science Capstone, building upon my original CS-320 Software Testing and QA project. The enhancement transforms a basic command-line service application into a full-featured web application with a modern user interface.

## Features

### Patient Management
- Add new patients with comprehensive information validation
- View all patients in a sortable, searchable table
- Edit existing patient information
- Delete patients with confirmation prompts
- Unique patient ID generation (PAT1001, PAT1002, etc.)

### Appointment Scheduling
- Schedule appointments with date/time validation
- Link appointments to existing patients
- Assign doctors to appointments
- Add appointment descriptions and notes
- Edit and cancel existing appointments
- Unique appointment ID generation (APT2001, APT2002, etc.)

### Data Validation
- Server-side validation for all user inputs
- Email format validation
- Phone number format validation (10 digits)
- Future date validation for appointments
- Field length restrictions with user feedback

### User Interface
- Responsive Bootstrap-based design
- Clean, professional theme
- Intuitive navigation between sections
- Success/error message notifications
- Confirmation dialogs for destructive actions

## Technology Stack

### Backend
- **Java 21** - Core programming language
- **Spring Boot 3.5.5** - Application framework
- **Spring MVC** - Web framework
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database for development
- **Maven** - Build automation and dependency management

### Frontend
- **Thymeleaf** - Server-side templating engine
- **Bootstrap 5.3.0** - CSS framework
- **Bootstrap Icons** - Icon library
- **HTML5/CSS3** - Markup and styling
- **JavaScript** - Client-side functionality

### Testing
- **JUnit 5** - Unit testing framework
- **Spring Boot Test** - Integration testing
- **Mockito** - Mocking framework

### Development Tools
- **Visual Studio Code** - Primary IDE
- **Git** - Version control
- **GitHub** - Repository hosting

## Architecture

The application follows the **Model-View-Controller (MVC)** architectural pattern:

### Model Layer
- `Patient.java` - Patient entity with validation annotations
- `Appointment.java` - Appointment entity with business rules

### Service Layer
- `PatientService.java` - Patient business logic and data operations
- `AppointmentService.java` - Appointment business logic and validation

### Controller Layer
- `HomeController.java` - Landing page routing
- `PatientController.java` - Patient management endpoints
- `AppointmentController.java` - Appointment management endpoints

### View Layer
- Thymeleaf templates in `src/main/resources/templates/`
- Bootstrap-based responsive design
- Custom CSS for medical application theming

### Key Design Patterns
- **Dependency Injection** - Spring's IoC container manages component relationships
- **Repository Pattern** - Service classes abstract data access
- **Data Transfer Objects** - Model classes transfer data between layers
- **Template Method** - Thymeleaf templates provide consistent UI structure

## Installation

### Prerequisites
- Java 21 or higher
- Maven 3.6+ (or use included Maven wrapper)
- Git

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/sumbernotas/MedicalAptApplication.git
   cd medical-appointment-app
   ```

2. **Build the application**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - Open your web browser
   - Navigate to `http://localhost:8080`
   - The application will start with an empty database

### Configuration

The application uses Spring Boot's auto-configuration. Key configuration files:

- `application.properties` - Database and server configuration
- `pom.xml` - Maven dependencies and build configuration

## Usage

### Getting Started

1. **Homepage** - Navigate to the main dashboard
2. **Add Patients** - Click "Manage Patients" → "Add New Patient"
3. **Schedule Appointments** - Click "Manage Appointments" → "Add New Appointment"
4. **View Records** - Use the list views to see all patients and appointments

### Patient Management

**Adding a Patient:**
1. Click "Add New Patient"
2. Fill in required information:
   - Name (max 25 characters)
   - Phone (exactly 10 digits)
   - Email (valid email format)
3. Click "Add Patient"

**Editing a Patient:**
1. Find the patient in the list
2. Click "Edit" next to their record
3. Modify the information
4. Click "Update Patient"

### Appointment Scheduling

**Creating an Appointment:**
1. Click "Add New Appointment"
2. Select a patient from the dropdown
3. Enter doctor's name (max 25 characters)
4. Choose a future date
5. Add description (max 40 characters)
6. Click "Save Appointment"

## Testing

The application includes comprehensive test coverage using JUnit 5.

### Test Structure

- `PatientServiceTest.java` - Unit tests for patient operations
- `AppointmentServiceTest.java` - Unit tests for appointment operations
- Test coverage includes validation, and CRUD operations

### Test Categories

**Patient Service Tests:**
- Adding valid/invalid patients
- ID generation and uniqueness
- Validation scenarios (name length, phone format, email format)
- CRUD operations (create, read, update, delete)

**Appointment Service Tests:**
- Creating appointments with validation
- Patient-appointment relationships
- Date validation (future dates only)


### Relationships
- **One-to-Many:** One Patient can have multiple Appointments
- **Referential Integrity:** Appointments must reference existing patients

## Future Enhancements

### Planned Improvement (Enhancement #3)

**Database Integration**
   - Replace in-memory storage with MySQL database
   - Add connection pooling and transaction management
   - Implement data persistence across application restarts


## Development Notes

### Project Evolution

This application represents a significant enhancement of a basic service-oriented project:

**Original Project (CS-320):**
- Command-line interface
- Basic CRUD operations
- JUnit testing
- In-memory data storage

**Enhanced Version (Capstone):**
- Full web application interface
- Spring Boot framework integration
- Responsive UI design
- Advanced validation and error handling
- Professional software architecture

### Key Learning Outcomes

- **Spring Boot Mastery:** Dependency injection, auto-configuration, MVC pattern
- **Web Development:** HTML/CSS, Thymeleaf templating, Bootstrap framework
- **Software Architecture:** Separation of concerns, layered architecture
- **Testing Practices:** Unit testing, validation testing, edge case coverage
- **Professional Development:** Git workflows, documentation, project management

## Author

**Summer Bernotas**  
Computer Science Student  
Southern New Hampshire University  
Capstone Project - Enhancement #1  

---

**Last Updated:** September 2025  
**Version:** 1.0.0  
**Status:** Complete - Ready for Capstone Submission
