package com.capstone.medicalapp.medical_appointment_app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.capstone.medicalapp.medical_appointment_app.model.Patient;

// Temporarily acts as in-memory database
@Service
public class PatientService {
    private final Map<String, Patient> patients = new HashMap<>();  // temporary in-memory database
    private final AtomicLong idGeneration = new AtomicLong(1000); // declares AtomicLong value to enable complex & unique ID generation

    // declares constant for input length 
    // TODO: redundant with HTML & testing, will fix with MySQL implementation
    public static final int NAME_LENGTH = 25;
    public static final int PHONE_LENGTH = 10;

    // generate a uniqiue patient ID, with prefix "PAT"
    private String generatePatientID() {
        return "PAT" + idGeneration.incrementAndGet();
    }

    // returns list of all patients in memory
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    // adds a new patient to the list with validation
    public Patient addPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be blank");
        }

        // validates the fields required for patient creation
        validatePatientData(patient);

        // generates a unique ID for specific patient and assigns
        String patientID = generatePatientID();
        patient.setPatientID(patientID);
        
        patients.put(patientID, patient);
        return patient;
    }

    // gets patient by their ID
    public Optional<Patient> getPatientById(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(patients.get(patientID));
    }

    // deletes a patient from the map by their ID 
    public boolean deletePatient(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return false;
        }

        return patients.remove(patientID) != null;
    }

    // allows user to update patient information
    public Optional<Patient> updatePatient(String patientID, Patient updatedPatient) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return Optional.empty();
        }

        if (!patients.containsKey(patientID)) {
            return Optional.empty();
        }

        validatePatientData(updatedPatient);

        updatedPatient.setPatientID(patientID);
        patients.put(patientID, updatedPatient);
        return Optional.of(updatedPatient);
    }

    // returns the current number of patients in map
    public int getPatientCount() {
        return patients.size();
    }

    // checks if a patients exists by their ID number
    public boolean patientExists(String patientId) {
        return patientId != null && patients.containsKey(patientId);
    }

    // validates all patients information inputs
    private void validatePatientData(Patient patient) {
        if (patient.getName() == null || patient.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be blank");
        }

        if (patient.getName().length() > NAME_LENGTH) {
            throw new IllegalArgumentException("Patient name cannot exceed " + NAME_LENGTH + " characters");
        }
        
        if (patient.getPhone() == null || patient.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient phone cannot be blank");
        }
        
        if (patient.getPhone().length() != PHONE_LENGTH) {
            throw new IllegalArgumentException("Patient phone must be exactly " + PHONE_LENGTH + " digits");
        }
        
        // Validate phone contains only digits
        if (!patient.getPhone().matches("\\d{10}")) {
            throw new IllegalArgumentException("Patient phone must contain only digits");
        }
        
        if (patient.getEmail() == null || patient.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient email cannot be blank");
        }
        
        // Basic email validation
        if (!patient.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Patient email must be a valid email address");
        }
    }
}
