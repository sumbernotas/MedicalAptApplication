package com.capstone.medicalapp.medical_appointment_app.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Appointment {
    private Long ID;
    private Long patientID;

    @NotBlank(message = "Doctor's name is required")
    @Size(max = 25, message = "Doctor's name cannot exceed 25 characters")
    private String doctorName;

    // declares Date variable for appt date - cannot be a date in the past
    @Future(message = "Appointment date must be in the future")
    private LocalDate aptDate;

    // declares a variable to hold appt description - cannot be blank and has a max length
    @NotBlank(message = "Description is required")
    @Size(max = 40, message = "Description cannot exceed 40 characters")
    private String description;

    public Appointment() {}

    public Appointment(Long ID, Long patientID, String doctorName, LocalDate aptDate, String description) {
        this.ID = ID;
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.aptDate = aptDate;
        this.description = description;
    }

    /////////
    /// class setters and getters
    /////////

    public Long getID() {return ID;}
    public void setID(Long ID) {this.ID = ID;}

    public Long getPatientID() {return patientID;}
    public void setPatientID(Long patientID) {this.patientID = patientID;}

    public String getDoctorName() {return doctorName;}
    public void setDoctorName(String doctorName) {this.doctorName = doctorName;}

    public LocalDate getAptDate() {return aptDate;}
    public void setAptDate(LocalDate aptDate) {this.aptDate = aptDate;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    
}
