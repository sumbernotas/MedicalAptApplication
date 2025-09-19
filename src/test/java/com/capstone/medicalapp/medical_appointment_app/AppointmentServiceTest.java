package com.capstone.medicalapp.medical_appointment_app;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capstone.medicalapp.medical_appointment_app.model.Appointment;
import com.capstone.medicalapp.medical_appointment_app.model.Patient;
import com.capstone.medicalapp.medical_appointment_app.service.AppointmentService;
import com.capstone.medicalapp.medical_appointment_app.service.PatientService;

@DisplayName("Appointment Service Tests")
class AppointmentServiceTest {
    
    private AppointmentService appointmentService;
    private PatientService patientService;
    private Appointment validAppointment;
    private String validPatientId;
    
    @BeforeEach
    void setUp() {
        patientService = new PatientService();
        appointmentService = new AppointmentService();

        try {
            java.lang.reflect.Field patientServiceField = AppointmentService.class.getDeclaredField("patientService");
            patientServiceField.setAccessible(true);
            patientServiceField.set(appointmentService, patientService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject PatientService into AppointmentService", e);
        }

        Patient patient = new Patient("John Doe", "1234567890", "john.doe@email.com");
        Patient addedPatient = patientService.addPatient(patient);
        validPatientId = addedPatient.getPatientID();

        validAppointment = new Appointment(validPatientId, "Dr. Smith", LocalDate.now().plusDays(7), "Checkup");
    }
        
    @Test
    @DisplayName("Should add valid appointment successfully")
    void shouldAddValidAppointment() {
        Appointment result = appointmentService.addAppointment(validAppointment);
        
        assertNotNull(result);
        assertNotNull(result.getAppointmentID());

        assertTrue(result.getAppointmentID().startsWith("APT"));
        
        assertEquals(validPatientId, result.getPatientID());
        assertEquals("Dr. Smith", result.getDoctorName());
        assertEquals("Checkup", result.getDescription());
    }
    
    @Test
    @DisplayName("Should generate unique appointment IDs")
    void shouldGenerateUniqueAppointmentIds() {
        Appointment appointment1 = new Appointment(validPatientId, "Dr. Smith", LocalDate.now().plusDays(7), "Checkup");
        Appointment appointment2 = new Appointment(validPatientId, "Dr. Johnson", LocalDate.now().plusDays(14), "Follow-up");
        
        appointmentService.addAppointment(appointment1);
        appointmentService.addAppointment(appointment2);

        assertNotEquals(appointment1.getAppointmentID(), appointment2.getAppointmentID());
    }
    
    @Test
    @DisplayName("Should throw exception when appointment is null")
    void shouldThrowExceptionWhenAppointmentIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appointmentService.addAppointment(null));
        assertEquals("Appointment cannot be null", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when patient does not exist")
    void shouldThrowExceptionWhenPatientDoesNotExist() {
        Appointment appointment = new Appointment("NONEXISTENT", "Dr. Smith", LocalDate.now().plusDays(7), "Checkup");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appointmentService.addAppointment(appointment));
        assertEquals("Patient with ID NONEXISTENT does not exist", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when appointment date is in past")
    void shouldThrowExceptionWhenDateInPast() {
        Appointment appointment = new Appointment(validPatientId, "Dr. Smith", LocalDate.now().minusDays(1), "Checkup");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appointmentService.addAppointment(appointment));
        assertEquals("Appointment date must be in the future", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when doctor name is null")
    void shouldThrowExceptionWhenDoctorNameIsNull() {
        Appointment appointment = new Appointment(validPatientId, null, LocalDate.now().plusDays(7), "Checkup");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appointmentService.addAppointment(appointment));
        assertEquals("Doctor name cannot be blank", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when doctor name is blank")
    void shouldThrowExceptionWhenDoctorNameIsEmpty() {
        Appointment appointment = new Appointment(validPatientId, "", LocalDate.now().plusDays(7), "Checkup");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> appointmentService.addAppointment(appointment));
        assertEquals("Doctor name cannot be blank", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when doctor name exceeds 25 characters")
    void shouldThrowExceptionWhenDoctorNameTooLong() {
        String longName = "This is a very long doctor name that exceeds 25 characters";
        Appointment appointment = new Appointment(validPatientId, longName, LocalDate.now().plusDays(7), "Checkup");
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> appointmentService.addAppointment(appointment));
        assertEquals("Doctor name cannot exceed 25 characters", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when description exceeds 40 characters")
    void shouldThrowExceptionWhenDescriptionTooLong() {
        String longDescription = "This description is way too long and exceeds the maximum allowed characters";
        Appointment appointment = new Appointment(validPatientId, "Dr. Smith", LocalDate.now().plusDays(7), longDescription);
 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> appointmentService.addAppointment(appointment));
        assertEquals("Description cannot exceed 40 characters", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should retrieve appointment by ID successfully")
    void shouldRetrieveAppointmentById() {
        Appointment addedAppointment = appointmentService.addAppointment(validAppointment);
        String appointmentId = addedAppointment.getAppointmentID();

        Optional<Appointment> result = appointmentService.getAppointmentById(appointmentId);

        assertTrue(result.isPresent());
        assertEquals(appointmentId, result.get().getAppointmentID());
    }
    
    @Test
    @DisplayName("Should return empty when appointment ID not found")
    void shouldReturnEmptyWhenAppointmentNotFound() {
        Optional<Appointment> result = appointmentService.getAppointmentById("APT9999");

        assertFalse(result.isPresent());
    }
    
    @Test
    @DisplayName("Should get all appointments successfully")
    void shouldGetAllAppointments() {
        appointmentService.addAppointment(validAppointment);
        Appointment secondAppointment = new Appointment(validPatientId, "Dr. Johnson", LocalDate.now().plusDays(14), "Follow-up");
        appointmentService.addAppointment(secondAppointment);
        
        List<Appointment> result = appointmentService.getAllAppointments();
        
        assertEquals(2, result.size());
    }
    
    @Test
    @DisplayName("Should delete appointment successfully")
    void shouldDeleteAppointmentSuccessfully() {
        Appointment addedAppointment = appointmentService.addAppointment(validAppointment);
        String appointmentId = addedAppointment.getAppointmentID();

        boolean result = appointmentService.deleteAppointment(appointmentId);

        assertTrue(result);
        assertFalse(appointmentService.appointmentExists(appointmentId));
    }
    
    @Test
    @DisplayName("Should return false when deleting non-existent appointment")
    void shouldReturnFalseWhenDeletingNonExistentAppointment() {
        boolean result = appointmentService.deleteAppointment("APT9999");

        assertFalse(result);
    }

    @Test
    @DisplayName("Should update appointment successfully")
    void shouldUpdateAppointmentSuccessfully() {
        Appointment addedAppointment = appointmentService.addAppointment(validAppointment);
        String appointmentId = addedAppointment.getAppointmentID();
        Appointment updatedAppointment = new Appointment(validPatientId, "Dr. Johnson", LocalDate.now().plusDays(14), "Follow-up");

        Optional<Appointment> result = appointmentService.updateAppointment(appointmentId, updatedAppointment);

        assertTrue(result.isPresent());

        assertEquals(appointmentId, result.get().getAppointmentID()); 
        assertEquals("Dr. Johnson", result.get().getDoctorName());
        assertEquals("Follow-up", result.get().getDescription());
    }
    
    @Test
    @DisplayName("Should return empty when updating non-existent appointment")
    void shouldReturnEmptyWhenUpdatingNonExistentAppointment() {
        Appointment updatedAppointment = new Appointment(validPatientId, "Dr. Johnson", LocalDate.now().plusDays(14), "Follow-up");

        Optional<Appointment> result = appointmentService.updateAppointment("APT9999", updatedAppointment);

        assertFalse(result.isPresent());
    }
}