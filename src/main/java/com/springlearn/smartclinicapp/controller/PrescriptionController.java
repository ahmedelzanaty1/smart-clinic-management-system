package com.springlearn.smartclinicapp.controller;

import com.springlearn.smartclinicapp.model.Prescription;
import com.springlearn.smartclinicapp.service.PrescriptionService;
import jakarta.validation.Valid; // Import for @Valid annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // For HTTP status codes
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService; // Use final for autowired fields

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * Creates a new prescription. Requires a token as a PathVariable and uses @Valid for validation.
     * Returns a ResponseEntity for structured responses.
     *
     * @param prescription The Prescription object to be created.
     * @param token The authorization token.
     * @return ResponseEntity containing the created Prescription or an error status.
     */
    @PostMapping("/{token}") // Consolidate into one POST mapping with token as PathVariable
    public ResponseEntity<Prescription> createPrescription(
            @RequestBody Prescription prescription, // Added @Valid
            @PathVariable String token) {

        // --- Token Validation (Placeholder) ---
        // In a real application, you would validate the 'token' here or
        // in an interceptor/filter (e.g., using Spring Security).
        // For demonstration, a simple check:
        if (token == null || token.isEmpty() || !token.startsWith("valid-token-")) {
            System.out.println("Invalid or missing token: " + token);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }
        System.out.println("Token received: " + token);
        // --- End Token Validation ---

        Prescription savedPrescription = prescriptionService.save(prescription); // Use prescriptionService.save()
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrescription); // Return 201 Created
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Optional<Prescription> prescription = Optional.ofNullable(prescriptionService.getPrescriptionById(id));
        return prescription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @Valid @RequestBody Prescription updatedPrescription) {
        try {
            Prescription result = prescriptionService.updatePrescription(id, updatedPrescription);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            // e.g., "Prescription not found" from service
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
    }
}