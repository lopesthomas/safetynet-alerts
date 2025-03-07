package com.safetynet.safetynet_alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.dto.ChildAlertDTO;
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

    public Person createPerson(Person person){
        return personRepository.createPerson(person);
    }

    public Person updatePerson(Person person){
        return personRepository.updatePerson(person).orElseThrow(() -> new IllegalArgumentException("Person not found"));
    }

    public void deletePerson(String firstName, String lastName) {
        personRepository.deletePerson(firstName, lastName).orElseThrow(() -> new IllegalArgumentException("Person not found"));
    }

    public List<ChildAlertDTO> getChildrenAtAddress(String address) {
        List<Person> personsAtAddress = personRepository.getAllPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        List<ChildAlertDTO> children = new ArrayList<>();

        for (Person person : personsAtAddress) {
            int age = getAge(person.getFirstName(), person.getLastName());
            if (age <= 18) {
                List<String> houseMembers = personsAtAddress.stream()
                        .filter(p -> !p.equals(person))
                        .map(p -> p.getFirstName() + " " + p.getLastName())
                        .collect(Collectors.toList());

                children.add(new ChildAlertDTO(person.getFirstName(), person.getLastName(), age, houseMembers));
            }
        }
        return children;
    }
}
