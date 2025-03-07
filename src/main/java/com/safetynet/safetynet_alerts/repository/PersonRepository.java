package com.safetynet.safetynet_alerts.repository;

import java.util.List;
import java.util.Optional;
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

    public Person createPerson(Person person) {
        dataLoader.getPersons().add(person);
        return person;
    }

    public Optional<Person> updatePerson(Person person) {
        for (Person p : dataLoader.getPersons()) {
            if (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())) {
                p.setAddress(person.getAddress());
                p.setCity(person.getCity());
                p.setZip(person.getZip());
                p.setPhone(person.getPhone());
                p.setEmail(person.getEmail());

                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Optional<Boolean> deletePerson(String firstName, String lastName) {
        boolean deleted = dataLoader.getPersons().removeIf(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
        if (deleted) {
            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
}
