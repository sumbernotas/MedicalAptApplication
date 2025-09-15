package com.capstone.medicalapp.medical_appointment_app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.capstone.medicalapp.medical_appointment_app.model.Patient;

// Temporarily acts as in-memory database
@Service
public class PatientService {
    private final Map<Long, Patient> patients = new HashMap<>();
    private Long nextId = 1L;

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    public Patient addPatient(Patient patient) {
        patient.setID(nextId++);
        patients.put(patient.getID(), patient);
        return patient;
    }

    public Optional<Patient> getPatientById(Long id) {
        return Optional.ofNullable(patients.get(id));
    }

    public boolean deletePatient(Long ID) {
        return patients.remove(ID) != null;
    }
}
