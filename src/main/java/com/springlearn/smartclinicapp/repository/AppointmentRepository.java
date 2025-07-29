package com.springlearn.smartclinicapp.repository;

import com.springlearn.smartclinicapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAll(Long doctorId, LocalDate date);
}
