package com.safetynet.safetynet_alerts.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.safetynet.safetynet_alerts.dto.ChildAlertDTO;
import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import com.safetynet.safetynet_alerts.repository.PersonRepository;

public class PersonServiceTest {
    
    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private List<MedicalRecord> medicalRecords;
    private List<Person> persons;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Doe", "01/01/2020", List.of(), List.of()));
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);

        persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "123 Street", "Paris", "00000", "0600000000", "test@mail.com"));
        when(personRepository.getAllPersons()).thenReturn(persons);
    }

    @Test
    void testCreatePerson() {
        Person newPerson = new Person("Jane", "Doe", "456 Avenue", "Paris", "00000", "0600000001", "jane@mail.com");
        when(personRepository.createPerson(newPerson)).thenReturn(newPerson);
        Person result = personService.createPerson(newPerson);
        assertEquals(newPerson, result);
    }

    @Test
    void testUpdatePerson() {
        Person updatedPerson = new Person("John", "Doe", "789 Boulevard", "Lyon", "00001", "0700000000", "testupdate@mail.com");
        when(personRepository.updatePerson(updatedPerson)).thenReturn(Optional.of(updatedPerson));
        Person result = personService.updatePerson(updatedPerson);
        assertEquals(updatedPerson, result);
    }

    @Test
    void testDeletePerson() {
        when(personRepository.deletePerson("John", "Doe")).thenReturn(Optional.of(true));
        assertDoesNotThrow(() -> personService.deletePerson("John", "Doe"));
    }

        
    @Test
    void testGetAge() {
        int age = personService.getAge("John", "Doe");
        assertEquals(5 , age); // Assuming the current year is 2023
    }

    @Test
    void testGetChildrenAtAddress() {
        List<ChildAlertDTO> children = personService.getChildrenAtAddress("123 Street");
        assertEquals(1, children.size());
        assertEquals("John", children.get(0).getFirstName());
        assertEquals("Doe", children.get(0).getLastName());
        assertEquals(5, children.get(0).getAge()); // Assuming the current year is 2023
    }
}
