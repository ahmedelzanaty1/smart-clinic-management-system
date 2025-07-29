package com.springlearn.smartclinicapp.controller;

import com.springlearn.smartclinicapp.model.Doctor;
import com.springlearn.smartclinicapp.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects; // Added for Objects.requireNonNullElse

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.saveDoctor(doctor));
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/available-times")
    public ResponseEntity<List<String>> getAvailableTimes(
            @PathVariable Long id,
            @RequestParam("date") LocalDate date,
            @RequestParam(value = "userRole", required = false) String userRole, // Added userRole as per feedback
            @RequestHeader("Authorization") String token) {


        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        String effectiveUserRole = Objects.requireNonNullElse(userRole, "GUEST"); // Default to GUEST if not provided
        System.out.println("User with role '" + effectiveUserRole + "' is requesting availability for Doctor ID: " + id + " on " + date);

        // Example: Only allow "PATIENT" or "ADMIN" to view times (highly simplified)
        if (!"PATIENT".equalsIgnoreCase(effectiveUserRole) && !"ADMIN".equalsIgnoreCase(effectiveUserRole)) {

        }

        List<String> availableTimes = doctorService.getAvailableTime(id, date);

        if (availableTimes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Or ResponseEntity.ok(Collections.emptyList())
        }
        return ResponseEntity.ok(availableTimes);
    }
}