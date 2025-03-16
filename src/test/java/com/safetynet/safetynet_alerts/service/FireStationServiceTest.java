package com.safetynet.safetynet_alerts.service;

import com.safetynet.safetynet_alerts.dto.FireStationResponseDTO;
import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.FireStationRepository;
import com.safetynet.safetynet_alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonService personService;

    @InjectMocks
    private FireStationService fireStationService;

    private List<FireStation> fireStations;
    private List<Person> persons;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fireStations = Arrays.asList(new FireStation("Address1", 1), new FireStation("Address2", 2));
        persons = Arrays.asList(
                new Person("John", "Doe", "Address1", "City", "12345", "123-456-7890", "john@email.com"),
                new Person("Jane", "Doe", "Address2", "City", "67890", "098-765-4321", "jane@email.com")
        );

        when(fireStationRepository.getAllFireStations()).thenReturn(fireStations);
        when(personRepository.getAllPersons()).thenReturn(persons);
    }

    @Test
    void testGetFireStations() {
        List<FireStation> result = fireStationService.getFireStations();
        assertEquals(2, result.size());
    }

    @Test
    void testFindByStationNumber() {
        List<FireStation> result = fireStationService.findByStationNumber(1);
        assertEquals(1, result.size());
        assertEquals("Address1", result.get(0).getAddress());
    }

    @Test
    void testAddFireStation() {
        FireStation newFireStation = new FireStation("Address3", 3);
        when(fireStationRepository.addFireStation(newFireStation)).thenReturn(newFireStation);
        FireStation result = fireStationService.addFireStation(newFireStation);
        assertEquals(newFireStation, result);
    }

    @Test
    void testUpdateFireStation() {
        FireStation updatedFireStation = new FireStation("Address1", 4);
        when(fireStationRepository.updateFireStation(updatedFireStation)).thenReturn(updatedFireStation);
        FireStation result = fireStationService.updateFireStation(updatedFireStation);
        assertEquals(updatedFireStation, result);
    }

    @Test
    void testDeleteFireStation() {
        when(fireStationRepository.deleteFireStation("Address1")).thenReturn(Optional.of(true));
        boolean result = fireStationService.deleteFireStation("Address1");
        assertTrue(result);
    }

    @Test
    void testDeleteFireStationNotFound() {
        when(fireStationRepository.deleteFireStation(null)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> fireStationService.deleteFireStation(null));
    }

    @Test
    void testGetPersonsCoveredByStation() {
        when(personService.getAge("John", "Doe")).thenReturn(25);
        when(personService.getAge("Jane", "Doe")).thenReturn(1);

        FireStationResponseDTO result = fireStationService.getPersonsCoveredByStation(1);
        assertEquals(1, result.getAdultCount());
        // assertEquals(1, result.getChildCount());
    }

    @Test
    void testGetPhoneNumberByStationNumber() {
        List<String> result = fireStationService.getPhoneNumberByStationNumber(1);
        assertEquals(1, result.size());
        assertEquals("123-456-7890", result.get(0));
    }
}
