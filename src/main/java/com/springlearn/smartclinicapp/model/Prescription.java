package com.springlearn.smartclinicapp.model;


import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String medication;

    @Setter
    private String dosage;

    @Setter
    private String instructions;

    private LocalDate dateIssued;

    @Setter
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Constructors
    public Prescription() {}

    public Prescription(String medication, String dosage, String instructions, LocalDate dateIssued, Doctor doctor, Patient patient) {
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
        this.dateIssued = dateIssued;
        this.doctor = doctor;
        this.patient = patient;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getMedication() {
        return medication;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

}