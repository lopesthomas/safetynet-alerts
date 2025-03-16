package com.safetynet.safetynet_alerts.controller;

import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import com.safetynet.safetynet_alerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    private MedicalRecord medicalRecord;
    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", List.of("aznol:350mg"), List.of("nillacilan"));
        medicalRecords = Arrays.asList(medicalRecord, new MedicalRecord("Jane", "Doe", "02/02/2002", List.of("pharmacol:250mg"), List.of("peanuts")));
    }

    @Test
    void testGetMedicalRecords() {
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
        List<MedicalRecord> result = medicalRecordController.getMedicalRecords();
        assertEquals(medicalRecords, result);
    }

    @Test
    void testAddMedicalRecord() {
        when(medicalRecordService.addMedicalRecord(medicalRecord)).thenReturn(medicalRecord);
        ResponseEntity<MedicalRecord> response = medicalRecordController.addMedicalRecord(medicalRecord);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicalRecord, response.getBody());
    }

    @Test
    void testUpdateMedicalRecord() {
        when(medicalRecordService.updateMedicalRecord(medicalRecord)).thenReturn(medicalRecord);
        ResponseEntity<MedicalRecord> response = medicalRecordController.updateMedicalRecord(medicalRecord);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicalRecord, response.getBody());
    }

    @Test
    void testDeleteMedicalRecord() {
        doNothing().when(medicalRecordService).deleteMedicalRecord("John", "Doe");
        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord("John", "Doe");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(medicalRecordService, times(1)).deleteMedicalRecord("John", "Doe");
    }
}
