package com.capstone.medicalapp.medical_appointment_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstone.medicalapp.medical_appointment_app.model.Patient;
import com.capstone.medicalapp.medical_appointment_app.service.PatientService;


@Controller
@RequestMapping("/patients")
public class PatientController {
    
    @Autowired
    private PatientService patientService;

    // will list all patients
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    // Show form to add patient
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "add-patient"; // will look for add-patient.html
    }

    // Handle form submission
    @PostMapping
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.addPatient(patient);
        return "redirect:/patients";
    }

    // Delete a patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}

