package com.capstone.medicalapp.medical_appointment_app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.medicalapp.medical_appointment_app.model.Appointment;

// temporarily acts as in-memory database
@Service
public class AppointmentService {
    private final Map<String, Appointment> appointments = new HashMap<>(); // temporary in-memory database
    private final AtomicLong idGeneration = new AtomicLong(2000);

    // declares constant for input length 
    // TODO: redundant with HTML & testing, will fix with MySQL implementation
    private final byte DOCTOR_LENGTH = 25;
    private final byte DESCRIPTION_LENGTH = 40;

    @Autowired
    private PatientService patientService; 

    // generates a complex and unique apt ID with prefix
    private String generateAppointmentID() {
        return "APT" + idGeneration.incrementAndGet();
    }

    // returns all appointments within map "database"
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    // adds appointment to map
    public Appointment addAppointment(Appointment apt) {
        if (apt == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        
        // Validate apt data
        validateAppointmentData(apt);
        
        // Validate that patient exists
        if (!patientService.patientExists(apt.getPatientID())) {
            throw new IllegalArgumentException("Patient with ID " + apt.getPatientID() + " does not exist");
        }
        
        // Generate and set unique ID
        String appointmentId = generateAppointmentID();
        apt.setAppointmentID(appointmentId);
        
        // Store apt
        appointments.put(appointmentId, apt);
        return apt;
    }

    // gets an appointmentt by its ID
    public Optional<Appointment> getAppointmentById(String appointmentID) {
        if (appointmentID == null || appointmentID.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(appointments.get(appointmentID));
    }

    // deletes an apt by its ID
    public boolean deleteAppointment(String appointmentID) {
        if (appointmentID == null || appointmentID.trim().isEmpty()) {
            return false;
        }

        return appointments.remove(appointmentID) != null;
    }

    // updates an existing appointments information
    public Optional<Appointment> updateAppointment(String appointmentID, Appointment updatedApt) {
        if (appointmentID == null || appointmentID.trim().isEmpty()) {
            return Optional.empty();
        }

        if (!appointments.containsKey(appointmentID)) {
            return Optional.empty();
        }

        validateAppointmentData(updatedApt);

        if (!patientService.patientExists(updatedApt.getPatientID())) {
            throw new IllegalArgumentException("Patient with ID: " + updatedApt.getPatientID() + " does not exist");
        }

        updatedApt.setAppointmentID(appointmentID);
        appointments.put(appointmentID, updatedApt);
        return Optional.of(updatedApt);
    }

    // gets appointments for a specific patient
    public List<Appointment> getAppointmentsByPatientID(String patientID) {
        if (patientID == null || patientID.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return appointments.values().stream().filter(apt -> patientID.equals(apt.getPatientID())).toList();
    }

    // gets apt for a specific date
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        if (date == null) {
            return new ArrayList<>();
        }

        return appointments.values().stream().filter(apt -> date.equals(apt.getAptDate())).toList();
    }

    public int getAppointmentCount() {
        return appointments.size();
    }

    public boolean appointmentExists(String appointmentID) {
        return appointmentID != null && appointments.containsKey(appointmentID);
    }

    public int deleteAppointmentsByPatientId(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            return 0;
        }
        
        List<String> appointmentIdsToDelete = appointments.entrySet().stream()
                .filter(entry -> patientId.equals(entry.getValue().getPatientID()))
                .map(Map.Entry::getKey)
                .toList();
        
        appointmentIdsToDelete.forEach(appointments::remove);
        return appointmentIdsToDelete.size();
    }

    // validates appointment input information
    private void validateAppointmentData(Appointment apt) {
        // Validate patient ID
        if (apt.getPatientID() == null || apt.getPatientID().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be blank");
        }
        
        // Validate doctor name
        if (apt.getDoctorName() == null || apt.getDoctorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be blank");
        }
        
        if (apt.getDoctorName().length() > DOCTOR_LENGTH) {
            throw new IllegalArgumentException("Doctor name cannot exceed " + DOCTOR_LENGTH + " characters");
        }
        
        // Validate apt date
        if (apt.getAptDate() == null) {
            throw new IllegalArgumentException("Appointment date cannot be blank");
        }
        
        if (!apt.getAptDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date must be in the future");
        }
        
        // Validate description
        if (apt.getDescription() == null || apt.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        
        if (apt.getDescription().length() > DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Description cannot exceed " + DESCRIPTION_LENGTH + " characters");
        }
    }
}
