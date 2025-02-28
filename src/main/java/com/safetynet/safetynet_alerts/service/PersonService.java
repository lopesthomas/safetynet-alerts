package com.safetynet.safetynet_alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import com.safetynet.safetynet_alerts.repository.PersonRepository;

@Service
public class PersonService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private PersonRepository personRepository;

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

    public void addPerson(Person person){
        personRepository.addPerson(person);
    }

    public boolean updatePerson(Person person){
        return personRepository.updatePerson(person);
    }

    public boolean deletePerson(String firstName, String lastName) {
        return personRepository.deletePerson(firstName, lastName);
    }
}
