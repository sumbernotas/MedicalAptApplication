package com.capstone.medicalapp.medical_appointment_app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// creates a database table called "appointment" to store patient appointment data, data is persistent
@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    // specific appointment id for business use
    @Column(name = "appointment_code", unique = true, nullable = false, length = 20)
    private String appointmentID;

    @Column(name = "patient_code", nullable = false, length = 20)
    private String patientID;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate aptDate;

    // declares variable to hold doctors name with validation - cannot be blank and has a max length
    @NotBlank(message = "Doctor's name is required")
    @Size(max = 25, message = "Doctor's name cannot exceed 25 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-\\.]+$", message = "Doctor's name can only contain letters, spaces, hyphens, apostrophes, and periods")
    @Column(name = "doctor_name", nullable = false, length = 25)
    private String doctorName;

    // declares a variable to hold appt description w/ requirements
    @NotBlank(message = "Description is required")
    @Size(max = 40, message = "Description cannot exceed 40 characters")
    @Column(name = "description", nullable = false, length = 40)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // default constructor
    public Appointment() {}

    // constructor with all fields
    public Appointment(String appointmentID, String patientID, String doctorName, LocalDate aptDate, String description) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.aptDate = aptDate;
        this.description = description;
    }

    // constructor with ID, specifically for new appointments
    public Appointment(String patientID, String doctorName, LocalDate aptDate, String description) {
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.aptDate = aptDate;
        this.description = description;
    }

    //  stores data for when appointment was created and updated via time stamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /////////
    /// class setters and getters
    /////////

    public String getAppointmentID() {return appointmentID;}
    public void setAppointmentID(String appointmentID) {this.appointmentID = appointmentID;}

    public String getPatientID() {return patientID;}
    public void setPatientID(String patientID) {this.patientID = patientID;}

    public String getDoctorName() {return doctorName;}
    public void setDoctorName(String doctorName) {this.doctorName = doctorName;}

    public LocalDate getAptDate() {return aptDate;}
    public void setAptDate(LocalDate aptDate) {this.aptDate = aptDate;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "id= " + id +
                ", appointmentID='" + appointmentID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", aptDate=" + aptDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Appointment appointment = (Appointment) obj;
        return id != null ? id.equals(appointment.id) : appointment.id == null;
    }

    @Override
    public int hashCode() {
        return appointmentID != null ? appointmentID.hashCode() : 0;
    }
}
