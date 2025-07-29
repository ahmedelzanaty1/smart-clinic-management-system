package com.springlearn.smartclinicapp.service;

import com.springlearn.smartclinicapp.model.Prescription;
import com.springlearn.smartclinicapp.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription getPrescriptionById(Long id) {
        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(id);
        return optionalPrescription.orElse(null);
    }

    public Prescription updatePrescription(Long id, Prescription updatedPrescription) {
        Optional<Prescription> optional = prescriptionRepository.findById(id);
        if (optional.isPresent()) {
            Prescription existing = optional.get();
            existing.setDoctor(updatedPrescription.getDoctor());
            existing.setPatient(updatedPrescription.getPatient());
            return prescriptionRepository.save(existing);
        }
        return null;
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
}