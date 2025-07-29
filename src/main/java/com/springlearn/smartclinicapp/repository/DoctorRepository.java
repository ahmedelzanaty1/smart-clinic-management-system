package com.springlearn.smartclinicapp.repository;

import com.springlearn.smartclinicapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {}
