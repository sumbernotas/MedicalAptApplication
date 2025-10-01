package com.capstone.medicalapp.medical_appointment_app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// creates a database table called "patients" to store patient data, data is persistent
@Entity
@Table(name = "patients")
public class Patient {

    @Id // declares as primary key for database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    // patient specific id for business use
    @Column(name = "patient_code", unique = true, nullable = false, length = 20)
    private String patientID;

    // declares name variable for patient with requirements
    @NotBlank(message = "Name is required")
    @Size(max = 25, message = "Name cannot exceed 25 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Name can only contain letters, spaces, hyphens, and apostrophes")
    @Column(name = "name", nullable = false, length = 25)
    private String name;

    // declares variable to hold patient phone number w/ requirements
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    // declares varaible to hold patient email w/ requirements
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    // saves timestamps for patient creation and edits
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // default constructor
    public Patient() {}

    //constructor with all fields
    public Patient(String patientID, String name, String phone, String email) {
        this.patientID = patientID;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // constructor without ID, specifically for declaring new patients
    public Patient(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // stores time stamps for when patient was created and updated
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    ////////////
    /// class getters and setters
    ////////////
    
    public String getPatientID() {return patientID;}
    public void setPatientID(String patientID) {this.patientID = patientID;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", patientID='" + patientID + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Patient patient = (Patient) obj;
        return id != null ? id.equals(patient.id) : patient.id == null;
    }

    @Override
    public int hashCode() {
        return patientID != null ? patientID.hashCode() : 0;
    }
}
