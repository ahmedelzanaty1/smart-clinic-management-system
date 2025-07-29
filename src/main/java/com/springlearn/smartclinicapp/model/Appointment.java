package com.springlearn.smartclinicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future; // استيراد @Future
import jakarta.validation.constraints.NotNull; // استيراد @NotNull
import lombok.*;

import java.time.LocalDateTime; // استيراد LocalDateTime

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;


    @NotNull(message = "Appointment time cannot be null")
    @Future(message = "Appointment time must be in the future")
    private LocalDateTime appointmentTime;


    private String status; // مثلاً: "PENDING", "CONFIRMED", "COMPLETED", "CANCELED"




}