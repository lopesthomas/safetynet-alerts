package com.safetynet.safetynet_alerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<FireStation> getFireStations() {
        return fireStationRepository.getAllFireStations();
    }

    public List<FireStation> findByStationNumber(int stationNumber) {
        // Récupère les adresses couvertes par cette station
    return fireStationRepository.getAllFireStations().stream()
            .filter(f -> f.getStation() == stationNumber)
            .collect(Collectors.toList());
        
        
    }
    
    public List<Person> getPersonsCoveredByStation(int stationNumber){
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
        
        return persons;
    }

     
}
