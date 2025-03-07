package com.safetynet.safetynet_alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.addMedicalRecord(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.updateMedicalRecord(medicalRecord).orElseThrow(() -> new IllegalArgumentException("MedicalRecord not found"));
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordRepository.deleteMedicalRecord(firstName, lastName).orElseThrow(() -> new IllegalArgumentException("MedicalRecord not found"));
    }
}
