package com.capstone.medicalapp.medical_appointment_app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.medicalapp.medical_appointment_app.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    Optional<Appointment> findByAppointmentID(String appointmentID);
    
    List<Appointment> findByPatientID(String patientID);
    List<Appointment> findByAptDate(LocalDate date);
    
    boolean existsByAppointmentID(String appointmentID);
    
    void deleteByAppointmentID(String appointmentID);
    void deleteByPatientID(String patientID);
}