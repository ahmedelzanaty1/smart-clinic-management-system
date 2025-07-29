package com.springlearn.smartclinicapp.service;

import com.springlearn.smartclinicapp.model.Appointment;
import com.springlearn.smartclinicapp.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }


    public Appointment bookAppointment(Appointment appointment) {
        // يمكنك إضافة منطق أعمال إضافي هنا قبل الحفظ،
        // مثل التحقق من توفر الطبيب، مواعيد المريض الحالية، وما إلى ذلك.
        return appointmentRepository.save(appointment);
    }


    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
    }
}