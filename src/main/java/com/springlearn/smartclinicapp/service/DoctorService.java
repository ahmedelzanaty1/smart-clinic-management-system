package com.springlearn.smartclinicapp.service;

import com.springlearn.smartclinicapp.dto.Login;
import com.springlearn.smartclinicapp.model.Doctor;
import com.springlearn.smartclinicapp.repository.DoctorRepository;
import org.springframework.http.HttpStatus; // For ResponseEntity status
import org.springframework.http.ResponseEntity; // For ResponseEntity
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    public List<String> getAvailableTimes(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            if (doctor.getAvailableTimes() != null && !doctor.getAvailableTimes().isEmpty()) {

                System.out.println("Returning stored available times for doctor ID: " + doctorId + " on date: " + date);
                return doctor.getAvailableTimes();
            } else {
                // If the doctor has no specific available times set, return a default/empty.
                System.out.println("No specific available times defined for doctor ID: " + doctorId + ", returning empty list.");
                return Collections.emptyList();
            }
        }
        return Collections.emptyList(); // Return an empty list if doctor is not found
    }

    /**
     * Validates doctor login credentials and returns a token or error message.
     * This addresses the requirement for a login method returning ResponseEntity<Map<String, String>>.
     *
     * @param loginObject An object containing the doctor's email and password.
     * @return ResponseEntity<Map<String, String>> containing a "token" on success, or an "error" message on failure.
     */
    public ResponseEntity<Map<String, String>> loginDoctor(Login loginObject) {
        Map<String, String> response = new HashMap<>();

        if (loginObject == null || loginObject.getEmail() == null || loginObject.getPassword() == null) {
            response.put("error", "Email and password cannot be empty.");
            return ResponseEntity.badRequest().body(response); // 400 Bad Request
        }

        // 1. Find the doctor by email
        // Assumes DoctorRepository has findByEmail(String email) method.
        Doctor doctor = doctorRepository.findByEmail(loginObject.getEmail());

        if (doctor == null) {
            response.put("error", "Doctor not found with this email.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }

        // 2. Validate the password
        // IMPORTANT: In a real application, passwords should be hashed and compared securely
        // (e.g., using BCryptPasswordEncoder). For this problem, we'll use direct comparison.
        if (doctor.getPassword() != null && doctor.getPassword().equals(loginObject.getPassword())) {
            // 3. Generate a token (placeholder for now)
            String token = "auth_token_for_" + doctor.getId() + "_" + System.currentTimeMillis();
            response.put("token", token);
            response.put("message", "Login successful!");
            return ResponseEntity.ok(response); // 200 OK
        } else {
            response.put("error", "Invalid credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }
    }

    public List<String> getAvailableTime(Long id, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        if (date.isBefore(LocalDate.now())) {
            date = date.plusDays(1);
        }
        return null;
    }
}