package com.safetynet.safetynet_alerts.repository;

import com.safetynet.safetynet_alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordRepositoryTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private MedicalRecordRepository medicalRecordRepository;

    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Doe", "01/01/2000", List.of("aznol:350mg"), List.of("nillacilan")));
        when(dataLoader.getMedicalrecords()).thenReturn(medicalRecords);
    }

    @Test
    void testGetAllMedicalRecords() {
        List<MedicalRecord> result = medicalRecordRepository.getAllMedicalRecords();
        assertEquals(1, result.size());
    }

    @Test
    void testAddMedicalRecord() {
        MedicalRecord newMedicalRecord = new MedicalRecord("Jane", "Doe", "02/02/2002", List.of("pharmacol:250mg"), List.of("peanuts"));
        MedicalRecord result = medicalRecordRepository.addMedicalRecord(newMedicalRecord);
        assertEquals(newMedicalRecord, result);
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testUpdateMedicalRecord() {
        MedicalRecord updatedMedicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", List.of("aznol:350mg", "pharmacol:250mg"), List.of("nillacilan", "peanuts"));
        Optional<MedicalRecord> result = medicalRecordRepository.updateMedicalRecord(updatedMedicalRecord);
        assertTrue(result.isPresent());
        assertEquals(updatedMedicalRecord.getMedications(), result.get().getMedications());
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testUpdateMedicalRecordNotFound() {
        MedicalRecord updatedMedicalRecord = new MedicalRecord(null, null, null, null, null);
        Optional<MedicalRecord> result = medicalRecordRepository.updateMedicalRecord(updatedMedicalRecord);
        assertTrue(result.isEmpty());
        verify(dataLoader, never()).saveData();
    }

    @Test
    void testDeleteMedicalRecord() {
        Optional<Boolean> result = medicalRecordRepository.deleteMedicalRecord("John", "Doe");
        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testDeleteMedicalRecordNotFound() {
        Optional<Boolean> result = medicalRecordRepository.deleteMedicalRecord(null, null);
        assertTrue(result.isEmpty());
        verify(dataLoader, never()).saveData();
    }
}
