package com.safetynet.safetynet_alerts.service;

import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private MedicalRecord medicalRecord;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", List.of("aznol:350mg"), List.of("nillacilan"));
    }

    @Test
    void testAddMedicalRecord() {
        when(medicalRecordRepository.addMedicalRecord(medicalRecord)).thenReturn(medicalRecord);
        MedicalRecord result = medicalRecordService.addMedicalRecord(medicalRecord);
        assertEquals(medicalRecord, result);
    }

    @Test
    void testUpdateMedicalRecord() {
        MedicalRecord medicalRecordUpdated = new MedicalRecord("Johnr", "Doe", "01/01/2000", List.of("aznol:350mg"), List.of("nillacilan"));
        when(medicalRecordRepository.updateMedicalRecord(medicalRecordUpdated)).thenReturn(Optional.of(medicalRecordUpdated));
        MedicalRecord result = medicalRecordService.updateMedicalRecord(medicalRecordUpdated);
        assertEquals(medicalRecordUpdated, result);
        assertNotEquals(medicalRecordUpdated, medicalRecord);
    }

    @Test
    void testUpdateMedicalRecordNotFound() {
        when(medicalRecordRepository.updateMedicalRecord(medicalRecord)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecord));
    }

    @Test
    void testDeleteMedicalRecord() {
        when(medicalRecordRepository.deleteMedicalRecord("John", "Doe")).thenReturn(Optional.of(true));
        assertDoesNotThrow(() -> medicalRecordService.deleteMedicalRecord("John", "Doe"));
    }

    @Test
    void testDeleteMedicalRecordNotFound() {
        when(medicalRecordRepository.deleteMedicalRecord("John", "Doe")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> medicalRecordService.deleteMedicalRecord("John", "Doe"));
    }
}
