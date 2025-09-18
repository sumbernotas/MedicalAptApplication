package com.capstone.medicalapp.medical_appointment_app.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Patient {
    private String patientID;

    // declares variable to hold patient name - cannot be blank & has a max length
    @NotBlank(message = "Name is required")
    @Size(max = 25, message = "Name cannot exceed 25 characters")
    private String name;

    // declares a variable to hold patient phone number - cannot be blank and must be 10 digits/characters
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;

    // declares a varaible to hold patient email - must be a valid email address
    @Email(message = "Must be a valid email address")
    private String email;

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

    @Override
    public String toString() {
        return "Patient{" +
                "patientID='" + patientID + '\'' +
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
        return patientID != null ? patientID.equals(patient.patientID) : patient.patientID == null;
    }

    @Override
    public int hashCode() {
        return patientID != null ? patientID.hashCode() : 0;
    }
}
