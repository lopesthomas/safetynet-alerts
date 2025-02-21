package com.safetynet.safetynet_alerts.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.safetynet.safetynet_alerts.model.Person;

@Repository
public class PersonRepository {
    private final DataLoader dataLoader;

    public PersonRepository(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<Person> getAllPersons() {
        return dataLoader.getPersons();
    }

    public List<Person> findByLastName(String lastName) {
        return dataLoader.getPersons().stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }
}
