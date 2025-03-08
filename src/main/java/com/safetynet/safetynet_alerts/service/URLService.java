package com.safetynet.safetynet_alerts.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.dto.FireResponseDTO;
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
                personService.getAge(person.getFirstName(), person.getLastName()),
                medicalRecord.map(MedicalRecord::getMedications).orElse(List.of()),
                medicalRecord.map(MedicalRecord::getAllergies).orElse(List.of()),
                fireStationCoveredPersons.orElse(-1) // -1 si aucune caserne trouvée
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    
    }

}
