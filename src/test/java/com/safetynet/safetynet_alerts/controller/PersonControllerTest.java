package com.safetynet.safetynet_alerts.controller;

import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.PersonRepository;
import com.safetynet.safetynet_alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    private Person person;
    private List<Person> persons;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person("John", "Doe", "123 Street", "City", "12345", "123-456-7890", "john.doe@example.com");
        persons = Arrays.asList(person, new Person("Jane", "Doe", "456 Avenue", "City", "67890", "098-765-4321", "jane.doe@example.com"));
    }

    @Test
    void testGetAllPersons() {
        when(personRepository.getAllPersons()).thenReturn(persons);
        List<Person> result = personController.getAllPersons();
        assertEquals(persons, result);
    }

    @Test
    void testCreatePerson() {
        when(personService.createPerson(person)).thenReturn(person);
        ResponseEntity<Person> response = personController.createPerson(person);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(person, response.getBody());
    }

    @Test
    void testUpdatePerson() {
        when(personService.updatePerson(person)).thenReturn(person);
        ResponseEntity<Person> response = personController.updatePerson(person);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(person, response.getBody());
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personService).deletePerson("John", "Doe");
        ResponseEntity<Void> response = personController.deletePerson("John", "Doe");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personService, times(1)).deletePerson("John", "Doe");
    }
}
