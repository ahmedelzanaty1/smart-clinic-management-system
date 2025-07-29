package com.springlearn.smartclinicapp.controller;

import com.springlearn.smartclinicapp.model.Doctor;
import com.springlearn.smartclinicapp.service.DoctorService;
import com.springlearn.smartclinicapp.service.TokenService; // استيراد TokenService
import com.springlearn.smartclinicapp.dto.Login; // استيراد Login DTO إذا كنت ستضيفها هنا

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenService tokenService; // حقن TokenService

    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService; // تهيئة TokenService
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        // في تطبيق حقيقي، قد تحتاج إلى إضافة تحقق من الصلاحيات هنا
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        // في تطبيق حقيقي، تحقق من الصلاحيات قبل الحذف
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * مسار لاسترداد الأوقات المتاحة للطبيب بناءً على دور المستخدم، معرف الطبيب، التاريخ، والتوكن.
     * هذا التابع يلبي متطلبات السؤال 5.
     *
     * @param user دور المستخدم (مثلاً: "PATIENT", "ADMIN").
     * @param doctorId معرف الطبيب.
     * @param date التاريخ المطلوب.
     * @param token رمز المصادقة.
     * @return استجابة تحتوي على قائمة بالأوقات المتاحة أو رسالة خطأ.
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable LocalDate date,
            @PathVariable String token) {

        Map<String, Object> response = new HashMap<>();

        // 1. التحقق من صحة التوكن باستخدام TokenService
        // افترض أن `validateToken` يأخذ التوكن ويعيد `true` إذا كان صالحًا.
        // في سيناريو حقيقي، قد تحتاج إلى تمرير `user` أيضاً لـ `validateToken` للتحقق من الصلاحيات.
        if (!tokenService.validateToken(token)) {
            response.put("error", "Invalid or expired token.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }

        // 2. التحقق من صلاحيات الدور (مثال بسيط)
        // يمكنك هنا إضافة منطق أكثر تعقيداً للتحقق من أن الدور (user) لديه صلاحية الوصول.
        // مثلاً، يمكن للطبيب رؤية جدول مواعيده بالكامل، بينما يمكن للمريض فقط رؤية الأوقات المتاحة للحجز.
        if (!"PATIENT".equalsIgnoreCase(user) && !"ADMIN".equalsIgnoreCase(user)) {
            response.put("error", "User role not authorized to view doctor availability.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); // 403 Forbidden
        }

        // 3. جلب الأوقات المتاحة من DoctorService
        List<String> availableTimes = doctorService.getAvailableTimes(doctorId, date);

        if (availableTimes.isEmpty()) {
            response.put("message", "No available times for this doctor on " + date + ".");
            // يمكن أن تكون 204 No Content أو 200 OK مع قائمة فارغة
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("doctorId", doctorId);
            response.put("date", date.toString());
            response.put("availableTimes", availableTimes);
            return ResponseEntity.ok(response);
        }
    }

    // إضافة مسار تسجيل الدخول للطبيب هنا، كما تم الاتفاق عليه في سؤال 10
    // تأكد من وجود Login DTO
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginDoctor(@RequestBody Login loginObject) {
        // خدمة `loginDoctor` موجودة بالفعل في `DoctorService` بعد تعديلات سؤال 10
        return doctorService.loginDoctor(loginObject);
    }
}