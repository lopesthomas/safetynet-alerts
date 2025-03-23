package com.safetynet.safetynet_alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



/**
 * REST controller for managing medical record operations
 */
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private static Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    /**
     * Retrieves the list of all medical records
     *
     * @return a list of all medical records
     */
    @GetMapping("/all")
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }
    
    /**
     * Creates a new medical record
     *
     * @param medicalRecord the medical record to create
     * @return a ResponseEntity containing the created medical record
     */
    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord createdMedicalRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        logger.debug("Request: POST /medicalRecord", medicalRecord);
        return ResponseEntity.ok(createdMedicalRecord);
    }
    
    /**
     * Updates an existing medical record
     *
     * @param medicalRecord the medical record to update
     * @return a ResponseEntity containing the updated medical record
     */
    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
        logger.debug("Request: PUT /medicalRecord", medicalRecord);
        return ResponseEntity.ok(updatedMedicalRecord);
    }

    /**
     * Deletes a medical record by first name and last name
     *
     * @param firstName the first name of the person whose medical record is to be deleted
     * @param lastName the last name of the person whose medical record is to be deleted
     * @return a void ResponseEntity indicating the success of the deletion
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        logger.debug("Request: DELETE /medicalRecord?firstName=<firstName>&lastName=<lastName>", firstName, lastName);
        return ResponseEntity.noContent().build();
    }
}
