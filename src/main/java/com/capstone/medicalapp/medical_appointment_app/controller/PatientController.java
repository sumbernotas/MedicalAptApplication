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
        return "add-patient"; // will look for add-patient.html
    }

    // Handle form submission
    @PostMapping
    public String addPatient(@Valid @ModelAttribute Patient patient, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-patient";
        }

        try {
            Patient addedPatient = patientService.addPatient(patient);
            redirectAttributes.addFlashAttribute("successMessage", "Patient " + addedPatient.getName() + " has been successfully added with ID: " + addedPatient.getPatientID());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
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

