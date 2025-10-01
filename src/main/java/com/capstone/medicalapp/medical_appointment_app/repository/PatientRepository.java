package com.capstone.medicalapp.medical_appointment_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.medicalapp.medical_appointment_app.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByPatientID(String patientID);
    boolean existsByPatientID(String patientID);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    void deleteByPatientID(String patientID);
}
