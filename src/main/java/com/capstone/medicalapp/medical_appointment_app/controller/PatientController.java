package com.capstone.medicalapp.medical_appointment_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.capstone.medicalapp.medical_appointment_app.model.Patient;
import com.capstone.medicalapp.medical_appointment_app.service.PatientService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/patients")
public class PatientController {
    
    @Autowired
    private PatientService patientService;

    // displays all patients
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    // Shows form to add a patient
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        //model.addAttribute("nameMaxLength", patientService.NAME_LENGTH);
        //model.addAttribute("phoneMaxLength", patientService.PHONE_LENGTH);

        return "add-patient"; 
    }

    // shows form to add a new patient
    @PostMapping
    public String addPatient(@Valid @ModelAttribute Patient patient, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-patient";
        }

        try {
            Patient addedPatient = patientService.addPatient(patient);
            redirectAttributes.addFlashAttribute("successMessage", "Patient " + addedPatient.getName() + " added successfully with ID: " + addedPatient.getPatientID());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while adding the patient.");
        }
        
        return "redirect:/patients";
    }

    // shows form to edit a patient per patientID
    @GetMapping("/edit/{patientID}")
    public String showEditForm(@PathVariable String patientID, Model model, RedirectAttributes redirectAttributes) {
        var patient = patientService.getPatientById(patientID);
        
        if (patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            model.addAttribute("isEdit", true);
            return "add-patient"; // Reuse the same form for editing
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Patient with ID " + patientID + " not found");
            return "redirect:/patients";
        }
    }

    // updated patient info per ID
    @PostMapping("/update/{patientID}")
    public String updatePatient(@PathVariable String patientID, @Valid @ModelAttribute Patient patient, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-patient"; 
        }
        
        try {
            var updatedPatient = patientService.updatePatient(patientID, patient);
            if (updatedPatient.isPresent()) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Patient " + updatedPatient.get().getName() + " updated successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Patient with ID " + patientID + " not found");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "An unexpected error occurred while updating the patient.");
        }
        
        return "redirect:/patients";
    }

    // Delete a patient by ID
    @GetMapping("/delete/{patientID}")
    public String deletePatient(@PathVariable String patientID, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = patientService.deletePatient(patientID);

            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Patient with ID: " + patientID + " has been deleted successfully");
            }
            else {
                redirectAttributes.addFlashAttribute("errorMessage", "Patient with ID: " + patientID + " was not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error with deleting patient: " + e.getMessage());
        }
        
        return "redirect:/patients";
    }
}

