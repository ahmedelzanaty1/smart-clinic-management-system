package com.springlearn.smartclinicapp.repository;

import com.springlearn.smartclinicapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {}
