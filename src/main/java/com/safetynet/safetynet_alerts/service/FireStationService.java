package com.safetynet.safetynet_alerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet_alerts.dto.FireStationResponseDTO;
import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.FireStationRepository;
import com.safetynet.safetynet_alerts.repository.PersonRepository;

@Service
public class FireStationService {
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    private static Logger logger = LoggerFactory.getLogger(FireStationService.class);

    public List<FireStation> getFireStations() {
        return fireStationRepository.getAllFireStations();
    }

    public List<FireStation> findByStationNumber(int stationNumber) {
        // Récupère les adresses couvertes par cette station
    return fireStationRepository.getAllFireStations().stream()
            .filter(f -> f.getStation() == stationNumber)
            .collect(Collectors.toList());
    }

    public FireStation addFireStation(FireStation fireStation) {
       return fireStationRepository.addFireStation(fireStation);
    }

    public FireStation updateFireStation(FireStation fireStation) {
        return fireStationRepository.updateFireStation(fireStation);
    }

    public boolean deleteFireStation(String address) {
        return fireStationRepository.deleteFireStation(address).orElseThrow(() -> new IllegalArgumentException("FireStation not found"));
    }

    public FireStationResponseDTO getPersonsCoveredByStation(int stationNumber){
        // Récupère les adresses couvertes par cette station
       List<String> coveredAddresses = fireStationRepository.getAllFireStations().stream()
               .filter(f -> f.getStation() == stationNumber)
               .map(FireStation::getAddress)
               .collect(Collectors.toList());

       // Filtrer les personnes habitant ces adresses
       List<Person> persons = personRepository.getAllPersons().stream()
               .filter(p -> coveredAddresses.contains(p.getAddress()))
               .collect(Collectors.toList());

       // Compter adultes et enfants
       int adults = (int) persons.stream().filter(p -> personService.getAge(p.getFirstName(), p.getLastName()) >= 18).count();
       int children = persons.size() - adults;

       System.out.println("StationNumber: " + stationNumber);
       System.out.println("Adults: " + adults);
       System.out.println("Childrens: " + children);
       logger.debug("Persons covered by station {} : {}", stationNumber, persons);
       
       return new FireStationResponseDTO(persons, adults, children);
   }

    public List<String> getPhoneNumberByStationNumber (int stationNumber){
        List<String> coveredAddresses = fireStationRepository.getAllFireStations().stream()
        .filter(f -> f.getStation() == stationNumber)
        .map(FireStation::getAddress)
        .collect(Collectors.toList());

        List<String> phoneList = personRepository.getAllPersons().stream()
                .filter(p -> coveredAddresses.contains(p.getAddress()))
                .map(Person::getPhone)
                .collect(Collectors.toList());
        
        logger.debug("Phone numbers covered by station {} : {}", stationNumber, phoneList);
        return phoneList;
    }
     
}
