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

import com.capstone.medicalapp.medical_appointment_app.model.Appointment;
import com.capstone.medicalapp.medical_appointment_app.service.AppointmentService;
import com.capstone.medicalapp.medical_appointment_app.service.PatientService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    // list out all appointments
    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("patients", patientService.getAllPatients());
        return "appointments";
    }

    // shows form to add a new appointment
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients());
        return "add-appointment";
    }

    // saves an appointment
    @PostMapping
    public String addAppointment(@Valid @ModelAttribute Appointment appointment, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            return "add-appointment";
        }
        
        try {
            Appointment addedAppointment = appointmentService.addAppointment(appointment);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Appointment for " + addedAppointment.getAptDate() + " has been successfully added with ID: " + addedAppointment.getAppointmentID());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while creating the appointment.");
        }

        return "redirect:/appointments";
    }

    // shows form to edit a pre-existing appointment
    @GetMapping("/edit/{appointmentID}")
    public String showEditForm(@PathVariable String appointmentID, Model model, RedirectAttributes redirectAttributes) {
        var appointment = appointmentService.getAppointmentById(appointmentID);
        
        if (appointment.isPresent()) {
            model.addAttribute("appointment", appointment.get());
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("isEdit", true);
            return "add-appointment"; // Reuse the same form
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment with ID " + appointmentID + " was not found");
            return "redirect:/appointments";
        }
    }

    // updates pre-existing appointment by ID
    @PostMapping("/update/{appointmentID}")
    public String updateAppointment(@PathVariable String appointmentID, @Valid @ModelAttribute Appointment appointment, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("isEdit", true);
            return "add-appointment";
        }
        
        try {
            var updatedAppointment = appointmentService.updateAppointment(appointmentID, appointment);
            if (updatedAppointment.isPresent()) {
                redirectAttributes.addFlashAttribute("successMessage", "Appointment updated successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Appointment with ID " + appointmentID + " not found");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
        }
        
        return "redirect:/appointments";
    }

    // deletes an appointment by ID
    @GetMapping("/delete/{appointmentID}")
    public String deleteAppointment(@PathVariable String appointmentID, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = appointmentService.deleteAppointment(appointmentID);

            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Appointment with ID: " + appointmentID + " has been deleted successfully");
            }
            else {
                redirectAttributes.addFlashAttribute("errorMessage", "Appointment with ID: " + appointmentID + " was not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error with deleting appointment: " + e.getMessage());
        }

        return "redirect:/appointments";
    }
    
}
