package com.safetynet.safetynet_alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import com.safetynet.safetynet_alerts.service.MedicalRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping("/all")
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }
    
    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord createdMedicalRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok(createdMedicalRecord);
    }
    
    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
        return ResponseEntity.ok(updatedMedicalRecord);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return ResponseEntity.noContent().build();
    }
}
