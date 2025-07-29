package com.springlearn.smartclinicapp.controller;

import com.springlearn.smartclinicapp.model.Prescription;
import com.springlearn.smartclinicapp.service.PrescriptionService;
import com.springlearn.smartclinicapp.service.TokenService; // استيراد TokenService للتحقق من التوكن
import jakarta.validation.Valid; // استيراد @Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final TokenService tokenService; // حقن TokenService

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, TokenService tokenService) {
        this.prescriptionService = prescriptionService;
        this.tokenService = tokenService;
    }

    /**
     * ينشئ وصفة طبية جديدة. يلبي متطلبات السؤال 7.
     * يتطلب توكن كـ @PathVariable ويستخدم @Valid للتحقق من صحة البيانات.
     * يعيد ResponseEntity للاستجابات المنظمة.
     *
     * @param prescription كائن الوصفة الطبية المراد إنشاؤه.
     * @param token رمز المصادقة.
     * @return ResponseEntity يحتوي على الوصفة الطبية التي تم إنشاؤها أو حالة خطأ.
     */
    @PostMapping("/{token}") // المسار يقبل التوكن كـ PathVariable
    public ResponseEntity<Prescription> createPrescription(
            @Valid @RequestBody Prescription prescription, // إضافة @Valid للتحقق من صحة المدخلات
            @PathVariable String token) {

        // --- التحقق من صحة التوكن (مثال بسيط) ---
        // في تطبيق حقيقي، يجب أن يتم التحقق من التوكن بشكل كامل (صلاحيته، هل هو منتهي، هل هو سليم).
        // ويمكن استدعاء خدمة TokenService هنا.
        if (!tokenService.validateToken(token)) { // استخدام TokenService للتحقق
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }
        // يمكن إضافة منطق للتحقق من دور المستخدم من التوكن هنا أيضاً
        // if (!tokenService.getRoleFromToken(token).equals("DOCTOR")) { return FORBIDDEN; }

        Prescription savedPrescription = prescriptionService.save(prescription); // افتراض أن `save` موجود في الخدمة
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrescription); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptions); // إعادة ResponseEntity
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Optional<Prescription> prescription = Optional.ofNullable(prescriptionService.getPrescriptionById(id));
        return prescription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody Prescription updatedPrescription) { // إضافة @Valid
        try {
            Prescription result = prescriptionService.updatePrescription(id, updatedPrescription);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) { // Catch specific exceptions if needed (e.g., NotFoundException)
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}