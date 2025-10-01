package com.capstone.medicalapp.medical_appointment_app.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medicalapp.medical_appointment_app.model.Patient;
import com.capstone.medicalapp.medical_appointment_app.repository.PatientRepository;

// ensures that patient database functions and stays consistent
@Service
@Transactional 
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    private final AtomicLong idGeneration = new AtomicLong(1000);
    
    public static final int NAME_LENGTH = 25;
    public static final int PHONE_LENGTH = 10;
    
    private String generatePatientID() {
        return "PAT" + idGeneration.incrementAndGet();
    }
    
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    // adds a new patient to the database
    public Patient addPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
        
        validatePatientData(patient);
        
        String patientID = generatePatientID();
        patient.setPatientID(patientID);
        
        return patientRepository.save(patient);
    }
    
    // gets patient from database by patient id
    public Optional<Patient> getPatientById(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return Optional.empty();
        }
        return patientRepository.findByPatientID(patientID);
    }
    
    public boolean deletePatient(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return false;
        }
        
        Optional<Patient> patient = patientRepository.findByPatientID(patientID);
        if (patient.isPresent()) {
            patientRepository.delete(patient.get());
            return true;
        }
        return false;
    }
    
    // updates patient in database according to their specific id
    public Optional<Patient> updatePatient(String patientID, Patient updatedPatient) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return Optional.empty();
        }
        
        Optional<Patient> existingPatient = patientRepository.findByPatientID(patientID);
        if (existingPatient.isEmpty()) {
            return Optional.empty();
        }
        
        validatePatientData(updatedPatient);
        
        Patient patient = existingPatient.get();
        patient.setName(updatedPatient.getName());
        patient.setPhone(updatedPatient.getPhone());
        patient.setEmail(updatedPatient.getEmail());
        
        return Optional.of(patientRepository.save(patient));
    }
    
    // returns the number of patients in database
    public int getPatientCount() {
        return (int) patientRepository.count();
    }
    
    // checks and returns if a patient exists in database by their id
    public boolean patientExists(String patientId) {
        return patientId != null && patientRepository.existsByPatientID(patientId);
    }
    
    // validates information for adding patient into database
    private void validatePatientData(Patient patient) {
        if (patient.getName() == null || patient.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be blank");
        }
        
        if (patient.getName().length() > NAME_LENGTH) {
            throw new IllegalArgumentException("Patient name cannot exceed " + NAME_LENGTH + " characters");
        }
        
        if (!patient.getName().matches("^[a-zA-Z\\s'-]+$")) {
            throw new IllegalArgumentException("Patient name can only contain letters, spaces, hyphens, and apostrophes");
        }
        
        if (patient.getPhone() == null || patient.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient phone cannot be blank");
        }
        
        if (patient.getPhone().length() != PHONE_LENGTH) {
            throw new IllegalArgumentException("Patient phone must be exactly " + PHONE_LENGTH + " digits");
        }
        
        if (!patient.getPhone().matches("^\\d{10}$")) {
            throw new IllegalArgumentException("Patient phone must contain only digits");
        }
        
        if (patient.getEmail() == null || patient.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient email cannot be blank");
        }
        
        if (!patient.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Patient email must be a valid email address");
        }
    }
}

