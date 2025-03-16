package com.safetynet.safetynet_alerts.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.controller.URLController;
import com.safetynet.safetynet_alerts.dto.FireResponseDTO;
import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.FireStationRepository;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import com.safetynet.safetynet_alerts.repository.PersonRepository;

@Service
public class URLService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private static Logger logger = LoggerFactory.getLogger(URLService.class);

    public ResponseEntity<List<FireResponseDTO>> getPersonsByAddress(String address) {
        List<Person> residents = personRepository.getAllPersons().stream()
        .filter(p -> p.getAddress().equalsIgnoreCase(address))
        .collect(Collectors.toList());

        Optional<Integer> fireStationCoveredPersons = fireStationRepository.getAllFireStations().stream()
        .filter(f -> f.getAddress().equalsIgnoreCase(address))
        .map(f -> f.getStation()).findFirst();

        List<FireResponseDTO> responseList = residents.stream().map(person -> {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.getAllMedicalRecords().stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                             m.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst();
            return new FireResponseDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                null,
                personService.getAge(person.getFirstName(), person.getLastName()),
                medicalRecord.map(MedicalRecord::getMedications).orElse(List.of()),
                medicalRecord.map(MedicalRecord::getAllergies).orElse(List.of()),
                fireStationCoveredPersons.orElse(null) // si aucune caserne trouvée
            );
        }).collect(Collectors.toList());
        logger.info("Persons {} for this address {}", responseList, address);

        return ResponseEntity.ok(responseList);
    
    }

    // public List<FireResponseDTO> getPersonsInfoByFireStationNumber(List<Integer> stations) {
    //     List<FireResponseDTO> reponseList = stations.stream()
    //     .flatMap(stations -> fireStationRepository.getAllFireStations().stream()
    //         .filter(f -> f.getStation() == stations))

    // }

    public ResponseEntity<List<FireResponseDTO>> getPersonInfoByLastName(String lastName) {
        List<Person> listedPerson = personRepository.getAllPersons().stream()
        .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
        .collect(Collectors.toList());

        List<FireResponseDTO> responseList = listedPerson.stream().map(person -> {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.getAllMedicalRecords().stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                             m.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst();
    
            return new FireResponseDTO(
                person.getFirstName(),
                person.getLastName(),
                null,
                person.getEmail(),
                personService.getAge(person.getFirstName(), person.getLastName()),
                medicalRecord.map(MedicalRecord::getMedications).orElse(List.of()),
                medicalRecord.map(MedicalRecord::getAllergies).orElse(List.of()),
                null // Pas de caserne ici donc on peut mettre null
            );
        }).collect(Collectors.toList());
    
        return ResponseEntity.ok(responseList);
    }

    public List<String> getEmailByCity(String city) {
        return personRepository.getAllPersons().stream()
        .filter(p -> p.getCity().equalsIgnoreCase(city))
        .map(Person::getEmail)
        .collect(Collectors.toList());
    }

}
