package com.springlearn.smartclinicapp.repository;

import com.springlearn.smartclinicapp.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    // ممكن تضيف Queries مخصصة هنا لاحقًا لو حبيت
}
