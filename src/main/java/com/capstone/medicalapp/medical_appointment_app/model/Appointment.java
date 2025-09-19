package com.capstone.medicalapp.medical_appointment_app.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Appointment {
    // declares variabl
    private String appointmentID;
    private String patientID;
    private LocalDate aptDate;

    // declareds variable to hold doctors name with first layer of validation - cannot be blank and has a max length
    @NotBlank(message = "Doctor's name is required")
    @Size(max = 25, message = "Doctor's name cannot exceed 25 characters")
    private String doctorName;

    // declares a variable to hold appt description - cannot be blank and has a max length
    @NotBlank(message = "Description is required")
    @Size(max = 40, message = "Description cannot exceed 40 characters")
    private String description;

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
    
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID='" + appointmentID + '\'' +
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
        return appointmentID != null ? appointmentID.equals(appointment.appointmentID) : appointment.appointmentID == null;
    }

    @Override
    public int hashCode() {
        return appointmentID != null ? appointmentID.hashCode() : 0;
    }
}
