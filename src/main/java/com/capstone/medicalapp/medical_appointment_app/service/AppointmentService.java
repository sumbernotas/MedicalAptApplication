package com.capstone.medicalapp.medical_appointment_app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medicalapp.medical_appointment_app.model.Appointment;
import com.capstone.medicalapp.medical_appointment_app.repository.AppointmentRepository;

// ensures database is consistent
@Service
@Transactional
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private PatientService patientService;
    
    private final AtomicLong idGeneration = new AtomicLong(2000);
    
    private static final byte DOCTOR_LENGTH = 25;
    private static final byte DESCRIPTION_LENGTH = 40;
    
    private String generateAppointmentID() {
        return "APT" + idGeneration.incrementAndGet();
    }
    
    // returns all scheduled appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    // adds a new appointment to the database using pre-existing patient in database
    public Appointment addAppointment(Appointment apt) {
        if (apt == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        
        validateAppointmentData(apt);
        
        if (!patientService.patientExists(apt.getPatientID())) {
            throw new IllegalArgumentException("Patient with ID " + apt.getPatientID() + " does not exist");
        }
        
        String appointmentId = generateAppointmentID();
        apt.setAppointmentID(appointmentId);
        
        return appointmentRepository.save(apt);
    }
    
    // returns an appointment by the id
    public Optional<Appointment> getAppointmentById(String appointmentID) {
        if (appointmentID == null || appointmentID.trim().isEmpty()) {
            return Optional.empty();
        }
        return appointmentRepository.findByAppointmentID(appointmentID);
    }
    
    public boolean deleteAppointment(String appointmentID) {
        if (appointmentID == null || appointmentID.trim().isEmpty()) {
            return false;
        }
        
        Optional<Appointment> appointment = appointmentRepository.findByAppointmentID(appointmentID);
        if (appointment.isPresent()) {
            appointmentRepository.delete(appointment.get());
            return true;
        }
        return false;
    }
    
    // updates an appointment according to the appt id
    public Optional<Appointment> updateAppointment(String appointmentID, Appointment updatedApt) {
        if (appointmentID == null || appointmentID.trim().isEmpty()) {
            return Optional.empty();
        }
        
        Optional<Appointment> existingApt = appointmentRepository.findByAppointmentID(appointmentID);
        if (existingApt.isEmpty()) {
            return Optional.empty();
        }
        
        validateAppointmentData(updatedApt);
        
        if (!patientService.patientExists(updatedApt.getPatientID())) {
            throw new IllegalArgumentException("Patient with ID: " + updatedApt.getPatientID() + " does not exist");
        }
        
        Appointment apt = existingApt.get();
        apt.setPatientID(updatedApt.getPatientID());
        apt.setDoctorName(updatedApt.getDoctorName());
        apt.setAptDate(updatedApt.getAptDate());
        apt.setDescription(updatedApt.getDescription());
        
        return Optional.of(appointmentRepository.save(apt));
    }
    
    // returns an appointment according to the patient id assigned to it
    public List<Appointment> getAppointmentsByPatientID(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return List.of();
        }
        return appointmentRepository.findByPatientID(patientID);
    }
    
    // returns appointments by date
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        if (date == null) {
            return List.of();
        }
        return appointmentRepository.findByAptDate(date);
    }
    
    // returns number of appointments in database
    public int getAppointmentCount() {
        return (int) appointmentRepository.count();
    }
    
    // checks if appt exists in database
    public boolean appointmentExists(String appointmentID) {
        return appointmentID != null && appointmentRepository.existsByAppointmentID(appointmentID);
    }
    
    // deletes an appointment according to to patient id assigned to it from the database
    public int deleteAppointmentsByPatientId(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            return 0;
        }
        
        List<Appointment> appointments = appointmentRepository.findByPatientID(patientId);
        appointmentRepository.deleteAll(appointments);
        return appointments.size();
    }
    
    // validate information for adding a new appointment into the database
    private void validateAppointmentData(Appointment apt) {
        if (apt.getPatientID() == null || apt.getPatientID().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be blank");
        }
        
        if (apt.getDoctorName() == null || apt.getDoctorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be blank");
        }
        
        if (apt.getDoctorName().length() > DOCTOR_LENGTH) {
            throw new IllegalArgumentException("Doctor name cannot exceed " + DOCTOR_LENGTH + " characters");
        }
        
        if (!apt.getDoctorName().matches("^[a-zA-Z\\s'-\\.]+$")) {
            throw new IllegalArgumentException("Doctor name can only contain letters, spaces, hyphens, apostrophes, and periods");
        }
        
        if (apt.getAptDate() == null) {
            throw new IllegalArgumentException("Appointment date cannot be blank");
        }
        
        if (!apt.getAptDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date must be in the future");
        }
        
        if (apt.getDescription() == null || apt.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        
        if (apt.getDescription().length() > DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Description cannot exceed " + DESCRIPTION_LENGTH + " characters");
        }
    }
}
