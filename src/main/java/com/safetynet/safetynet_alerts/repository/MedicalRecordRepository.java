package com.safetynet.safetynet_alerts.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.safetynet_alerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {
    private final DataLoader dataLoader;

    public MedicalRecordRepository(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataLoader.getMedicalrecords();
    }
}
