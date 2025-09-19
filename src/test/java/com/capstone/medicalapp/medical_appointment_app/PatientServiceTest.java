package com.capstone.medicalapp.medical_appointment_app;

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

import com.capstone.medicalapp.medical_appointment_app.model.Patient;
import com.capstone.medicalapp.medical_appointment_app.service.PatientService;


@DisplayName("Patient Service Tests")
class PatientServiceTest {
    private PatientService patientService;
    private Patient patient;

    @BeforeEach
    void setUp() {
        patientService = new PatientService();
        patient = new Patient("Jane Doe", "1234567890", "jane.doe@gmail.com");
    }

    @Test
    @DisplayName("Should add patient successfully")
    void shouldAddValidPatient() {
        Patient result = patientService.addPatient(patient);

        assertNotNull(result);
        assertNotNull(result.getPatientID());
        
        assertTrue(result.getPatientID().startsWith("PAT"));

        assertEquals("Jane Doe", result.getName());
        assertEquals("1234567890", result.getPhone());
        assertEquals("jane.doe@gmail.com", result.getEmail());
    }

    @Test
    @DisplayName("Should create unique ID for every patient successfully")
    void shouldCreateUniqueID() {
        Patient patient1 = new Patient("Jane Doe", "1234567890", "jane.doe@email.com");
        Patient patient2 = new Patient("John Doe", "0987654321", "john.doe@email.com");

        patientService.addPatient(patient1);
        patientService.addPatient(patient2);

        assertNotNull(patient1.getPatientID());
        assertNotNull(patient2.getPatientID());
        assertNotEquals(patient1.getPatientID(), patient2.getPatientID());
    }

    @Test
    @DisplayName("Should throw an exception when patient is null")
    void exceptionWhenPatientNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(null));
        assertEquals("Patient cannot be blank", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw an exception when patient name is null")
    void exceptionWhenNameNull() {
        Patient patient = new Patient(null, "1234567890", "test@email.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
        assertEquals("Patient name cannot be blank", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when name is empty")
    void exceptionWhenNameIsEmpty() {
        Patient patient = new Patient("", "1234567890", "test@email.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
        assertEquals("Patient name cannot be blank", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when name exceeds 25 characters")
    void exceptionWhenNameTooLong() {
        String longName = "This is a very long name that exceeds twenty-five characters";
        Patient patient = new Patient(longName, "1234567890", "test@email.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
        assertEquals("Patient name cannot exceed 25 characters", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when phone is null")
    void exceptionWhenPhoneIsNull() {
        Patient patient = new Patient("John Doe", null, "test@email.com");
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
        assertEquals("Patient phone cannot be blank", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when phone is not 10 digits")
    void exceptionWhenPhoneWrongLength() {
        Patient patient = new Patient("John Doe", "12345678", "test@email.com"); 

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
        assertEquals("Patient phone must be exactly 10 digits", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when phone contains non-digits")
    void exceptionWhenPhoneContainsNonDigits() {
        Patient patient = new Patient("John Doe", "123-456-789", "test@email.com");
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
        assertEquals("Patient phone must be exactly 10 digits", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when email is invalid")
    void exceptionWhenEmailIsInvalid() {
        Patient patient = new Patient("John Doe", "1234567890", "invalid-email");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.addPatient(patient));
        assertEquals("Patient email must be a valid email address", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve patient by ID successfully")
    void retrievePatientById() {
        Patient addedPatient = patientService.addPatient(patient);
        String patientId = addedPatient.getPatientID();
        
        Optional<Patient> result = patientService.getPatientById(patientId);
        
        assertTrue(result.isPresent());

        assertEquals(patientId, result.get().getPatientID());
        assertEquals("Jane Doe", result.get().getName());
    }
    
    @Test
    @DisplayName("Should return empty when patient ID not found")
    void returnEmptyWhenPatientNotFound() {
        Optional<Patient> result = patientService.getPatientById("PAT9999");
 
        assertFalse(result.isPresent());
    }
    
    @Test
    @DisplayName("Should return empty when patient ID is null")
    void returnEmptyWhenIdIsNull() {
        Optional<Patient> result = patientService.getPatientById(null);

        assertFalse(result.isPresent());
    }
    
    @Test
    @DisplayName("Should get all patients successfully")
    void getAllPatients() {
        Patient patient1 = new Patient("John Doe", "1234567890", "john@email.com");
        Patient patient2 = new Patient("Jane Smith", "9876543210", "jane@email.com");
        
        patientService.addPatient(patient1);
        patientService.addPatient(patient2);

        List<Patient> result = patientService.getAllPatients();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should delete patient successfully")
    void deletePatientSuccessfully() {
        Patient addedPatient = patientService.addPatient(patient);
        String patientId = addedPatient.getPatientID();

        boolean result = patientService.deletePatient(patientId);

        assertTrue(result);
        assertFalse(patientService.patientExists(patientId));
    }
    
    @Test
    @DisplayName("Should return false when deleting non-existent patient")
    void returnFalseWhenDeletingNonExistentPatient() {
        boolean result = patientService.deletePatient("PAT9999");

        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should return false when patient ID is null")
    void returnFalseWhenIdIsNull() {
        boolean result = patientService.deletePatient(null);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should update patient successfully")
    void updatePatientSuccessfully() {
        Patient addedPatient = patientService.addPatient(patient);
        String patientId = addedPatient.getPatientID();
        Patient updatedPatient = new Patient("Jane Doe", "9876543210", "jane.doe@email.com");

        Optional<Patient> result = patientService.updatePatient(patientId, updatedPatient);

        assertTrue(result.isPresent());

        assertEquals(patientId, result.get().getPatientID()); // ID should remain the same
        assertEquals("Jane Doe", result.get().getName());
        assertEquals("9876543210", result.get().getPhone());
        assertEquals("jane.doe@email.com", result.get().getEmail());
    }
    
    @Test
    @DisplayName("Should return empty when updating non-existent patient")
    void returnEmptyWhenUpdatingNonExistentPatient() {
        Patient updatedPatient = new Patient("Jane Doe", "9876543210", "jane.doe@email.com");

        Optional<Patient> result = patientService.updatePatient("PAT9999", updatedPatient);

        assertFalse(result.isPresent());
    }
}

