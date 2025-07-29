package com.springlearn.smartclinicapp.repository;

import com.springlearn.smartclinicapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);
}