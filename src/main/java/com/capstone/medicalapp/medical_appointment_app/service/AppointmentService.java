package com.capstone.medicalapp.medical_appointment_app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.capstone.medicalapp.medical_appointment_app.model.Appointment;

@Service
public class AppointmentService {
    private final Map<Long, Appointment> appointments = new HashMap<>();
    private Long nextId = 1L;

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }
 
    public Appointment addAppointment(Appointment apt) {
        apt.setID(nextId++);
        appointments.put(apt.getID(), apt);
        return apt;
    }

    public boolean deleteAppointment(Long ID) {
        return appointments.remove(ID) != null;
    }
}
