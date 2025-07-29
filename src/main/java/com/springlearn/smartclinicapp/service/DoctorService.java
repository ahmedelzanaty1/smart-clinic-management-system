package com.springlearn.smartclinicapp.service;



import com.springlearn.smartclinicapp.model.Doctor;
import com.springlearn.smartclinicapp.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    public List<String> getAvailableTime(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {

            System.out.println("Fetching available times for doctor ID: " + doctorId);

            return List.of("09:00 AM - 10:00 AM", "10:30 AM - 11:30 AM", "02:00 PM - 03:00 PM");
        }
        return Collections.emptyList(); // Return an empty list if doctor is not found
    }
    public boolean validateDoctor(String email, String password) {
        Doctor d = doctorRepository.findByEmail(email);
        return d != null && d.getPassword().equals(password);
    }

    public List<String> getAvailableTimes(Long id, LocalDate date) {
        // نفترض availableTimes موجودة
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        return doctor.getAvailableTimes();
    }




}
