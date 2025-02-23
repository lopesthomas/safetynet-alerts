package com.safetynet.safetynet_alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;

@Service
public class PersonService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public int getAge(String firstName, String lastName) {
        return medicalRecordRepository.getAllMedicalRecords().stream()
                .filter(record -> record.getFirstName().equalsIgnoreCase(firstName)
                        && record.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(record -> calculateAge(record.getBirthdate()))
                .orElseThrow(() -> new IllegalArgumentException("Medical record not found for " + firstName + " " + lastName));
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
