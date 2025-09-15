package com.capstone.medicalapp.medical_appointment_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstone.medicalapp.medical_appointment_app.model.Appointment;
import com.capstone.medicalapp.medical_appointment_app.service.AppointmentService;
import com.capstone.medicalapp.medical_appointment_app.service.PatientService;


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
    public String addAppointment(@ModelAttribute Appointment apt) {
        appointmentService.addAppointment(apt);
        return "redirect:/appointments";
    }

    // deletes an appointment
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
    
}
