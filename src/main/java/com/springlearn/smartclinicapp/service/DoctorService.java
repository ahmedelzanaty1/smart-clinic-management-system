package com.springlearn.smartclinicapp.service;

import com.springlearn.smartclinicapp.model.Doctor;
import com.springlearn.smartclinicapp.repository.DoctorRepository;
import com.springlearn.smartclinicapp.dto.Login; // استيراد Login DTO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        // في تطبيق حقيقي، يجب تشفير كلمة المرور هنا قبل الحفظ
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

    /**
     * يسترد الأوقات المتاحة لطبيب معين في تاريخ محدد.
     * هذا التابع يلبي متطلب "retrieve a doctor's available time slots for a specific date".
     *
     * @param doctorId معرف الطبيب.
     * @param date التاريخ المطلوب.
     * @return قائمة بالأوقات المتاحة (سلاسل نصية).
     */
    public List<String> getAvailableTimes(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            // بما أن حقل `availableTimes` في نموذج Doctor هو `List<String>` بسيط،
            // فإنه لا يحتوي على معلومات التاريخ بشكل مباشر.
            // في تطبيق أكثر تعقيدًا، ستحتاج إلى هيكلة `availableTimes` بشكل يدعم التاريخ
            // (مثلاً: Map<LocalDate, List<LocalTime>>).
            // بناءً على المتطلبات الحالية والنموذج، سنعيد قائمة الأوقات المخزنة.
            // يمكن إضافة منطق لترشيح هذه الأوقات بناءً على اليوم من الأسبوع (عطلة نهاية الأسبوع مقابل أيام العمل)
            // إذا كان هذا جزءًا من المتوقع.
            if (doctor.getAvailableTimes() != null && !doctor.getAvailableTimes().isEmpty()) {
                System.out.println("Fetching available times for doctor ID: " + doctorId + " on date: " + date);
                // هنا يمكن إضافة منطق لترشيح الأوقات بناءً على 'date' إذا كانت الأوقات تحتوي على معلومات التاريخ
                // وإلا، فسيعيد كل الأوقات المخزنة للطبيب.
                return doctor.getAvailableTimes();
            } else {
                return Collections.emptyList(); // لا توجد أوقات متاحة محددة
            }
        }
        return Collections.emptyList(); // الطبيب غير موجود
    }

    /**
     * يتحقق من بيانات تسجيل دخول الطبيب ويعيد رمزًا (توكن) أو رسالة خطأ.
     * هذا التابع يلبي متطلب "validating doctor login credentials" ويعيد `ResponseEntity<Map<String, String>>`.
     *
     * @param loginObject كائن `Login` يحتوي على البريد الإلكتروني وكلمة المرور.
     * @return `ResponseEntity` يحتوي على توكن في حالة النجاح أو رسالة خطأ في حالة الفشل.
     */
    public ResponseEntity<Map<String, String>> loginDoctor(Login loginObject) {
        Map<String, String> response = new HashMap<>();

        if (loginObject == null || loginObject.getEmail() == null || loginObject.getPassword() == null) {
            response.put("error", "Email and password cannot be empty.");
            return ResponseEntity.badRequest().body(response); // 400 Bad Request
        }

        // 1. البحث عن الطبيب بالبريد الإلكتروني
        Doctor doctor = doctorRepository.findByEmail(loginObject.getEmail());

        if (doctor == null) {
            response.put("error", "Doctor not found with this email.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }

        // 2. التحقق من كلمة المرور
        // هام: في تطبيق حقيقي، يجب تشفير كلمات المرور (باستخدام BCryptPasswordEncoder مثلاً)
        // ومقارنتها بشكل آمن. هنا يتم استخدام مقارنة مباشرة للتبسيط.
        if (doctor.getPassword() != null && doctor.getPassword().equals(loginObject.getPassword())) {
            // 3. إنشاء توكن (مثال بسيط لتوكن)
            // في تطبيق حقيقي، يجب استخدام TokenService هنا لإنشاء JWT حقيقي.
            String generatedToken = "doctor_auth_token_" + doctor.getId() + "_" + System.currentTimeMillis();
            response.put("token", generatedToken);
            response.put("message", "Login successful!");
            return ResponseEntity.ok(response); // 200 OK
        } else {
            response.put("error", "Invalid credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }
    }
}