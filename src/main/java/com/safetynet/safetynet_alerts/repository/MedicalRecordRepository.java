package com.safetynet.safetynet_alerts.repository;

import java.util.List;
import java.util.Optional;

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

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        dataLoader.getMedicalrecords().add(medicalRecord);
        return medicalRecord;
    }

    public Optional<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord) {
        for (MedicalRecord mr : dataLoader.getMedicalrecords()) {
            if (mr.getFirstName().equals(medicalRecord.getFirstName()) && mr.getLastName().equals(medicalRecord.getLastName())) {
                mr.setBirthdate(medicalRecord.getBirthdate());
                mr.setMedications(medicalRecord.getMedications());
                mr.setAllergies(medicalRecord.getAllergies());

                return Optional.of(mr);
            }
        }
        return Optional.empty();
    }

    public Optional<Boolean> deleteMedicalRecord(String firstName, String lastName) {
        boolean deleted = dataLoader.getMedicalrecords().removeIf(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName));
        if (deleted) {
            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
}
