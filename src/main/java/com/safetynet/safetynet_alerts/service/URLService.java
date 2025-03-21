package com.safetynet.safetynet_alerts.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.dto.FireResponseDTO;
import com.safetynet.safetynet_alerts.dto.FloodResponseDTO;
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
        logger.debug("Persons {} for this address {}", responseList, address);

        return ResponseEntity.ok(responseList);
    
    }

    public List<FloodResponseDTO> getHouseholdsByFireStations(List<Integer> stationNumbers) {
        List<String> coveredAddresses = fireStationRepository.getAllFireStations().stream()
            .filter(f -> stationNumbers.contains(f.getStation()))
            .map(FireStation::getAddress)
            .distinct()
            .collect(Collectors.toList());

        Map<String, List<FireResponseDTO>> households = coveredAddresses.stream()
            .collect(Collectors.toMap(
                address -> address,
                address -> personRepository.getAllPersons().stream()
                    .filter(person -> person.getAddress().equalsIgnoreCase(address))
                    .map(person -> {
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
                            null
                        );
                    })
                    .collect(Collectors.toList())
            ));
            logger.debug("Households {} for this station {}", households, stationNumbers);

        return households.entrySet().stream()
            .map(entry -> new FloodResponseDTO(stationNumbers.toString(), entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }


    public ResponseEntity<List<FireResponseDTO>> getPersonByLastName(String lastName) {
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
                null
            );
        }).collect(Collectors.toList());
        logger.debug("Persons {} for this lastname {}", responseList, lastName);

    
        return ResponseEntity.ok(responseList);
    }

    public List<String> getEmailByCity(String city) {
        List<String> emails = personRepository.getAllPersons().stream()
        .filter(p -> p.getCity().equalsIgnoreCase(city))
        .map(Person::getEmail)
        .collect(Collectors.toList());
        logger.debug("Emails {} for this city {}", emails, city);

        return emails;
    }

}
