package com.safetynet.safetynet_alerts.repository;

import com.safetynet.safetynet_alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonRepositoryTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private PersonRepository personRepository;

    private List<Person> persons;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "123 Street", "City", "12345", "123-456-7890", "john.doe@example.com"));
        when(dataLoader.getPersons()).thenReturn(persons);
    }

    @Test
    void testGetAllPersons() {
        List<Person> result = personRepository.getAllPersons();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByLastName() {
        List<Person> result = personRepository.findByLastName("Doe");
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testCreatePerson() {
        Person newPerson = new Person("Jane", "Doe", "456 Avenue", "City", "67890", "098-765-4321", "jane.doe@example.com");
        Person result = personRepository.createPerson(newPerson);
        assertEquals(newPerson, result);
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testUpdatePerson() {
        Person updatedPerson = new Person("John", "Doe", "789 Boulevard", "City", "54321", "123-456-7890", "john.doe@example.com");
        Optional<Person> result = personRepository.updatePerson(updatedPerson);
        assertTrue(result.isPresent());
        assertEquals("789 Boulevard", result.get().getAddress());
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testUpdatePersonNotFound() {
        Person updatedPerson = new Person(null, null, null, null, null, null, null);
        Optional<Person> result = personRepository.updatePerson(updatedPerson);
        assertFalse(result.isPresent());
        verify(dataLoader, never()).saveData();
    }

    @Test
    void testDeletePerson() {
        Optional<Boolean> result = personRepository.deletePerson("John", "Doe");
        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testDeletePersonNotFound() {
        Optional<Boolean> result = personRepository.deletePerson(null, null);
        assertFalse(result.isPresent());
        verify(dataLoader, never()).saveData();
    }
}
