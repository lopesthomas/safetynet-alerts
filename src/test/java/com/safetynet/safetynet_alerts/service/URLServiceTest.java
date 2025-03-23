package com.safetynet.safetynet_alerts.service;

import com.safetynet.safetynet_alerts.dto.PersonResponseDTO;
import com.safetynet.safetynet_alerts.dto.FloodResponseDTO;
import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.FireStationRepository;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import com.safetynet.safetynet_alerts.repository.PersonRepository;
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

class URLServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonService personService;

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private URLService urlService;

    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        persons = Arrays.asList(
                new Person("John", "Doe", "Address1", "City", "12345", "123-456-7890", "john@test.com"),
                new Person("Jane", "Doe", "Address2", "City", "67890", "098-765-4321", "jane@test.com")
        );
        fireStations = Arrays.asList(new FireStation("Address1", 1));
        medicalRecords = Arrays.asList(new MedicalRecord("John", "Doe", "01/01/2000", List.of("aznol:350mg"), List.of("nillacilan")));

        when(personRepository.getAllPersons()).thenReturn(persons);
        when(fireStationRepository.getAllFireStations()).thenReturn(fireStations);
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);
    }

    @Test
    void testGetPersonsByAddress() {
        when(personService.getAge("John", "Doe")).thenReturn(25);

        ResponseEntity<List<PersonResponseDTO>> response = urlService.getPersonsByAddress("Address1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PersonResponseDTO> responseList = response.getBody();
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals("John", responseList.get(0).getFirstName());
    }

    @Test
    void testGetHouseholdsByFireStations() {
        when(personService.getAge("John", "Doe")).thenReturn(25);

        List<FloodResponseDTO> result = urlService.getHouseholdsByFireStations(List.of(1));
        assertEquals(1, result.size());
        assertEquals("Address1", result.get(0).getAddress());
        assertEquals("John", result.get(0).getResidents().get(0).getFirstName());
    }

    @Test
    void testGetPersonByLastName() {
        when(personService.getAge("Doe", "Doe")).thenReturn(25);

        ResponseEntity<List<PersonResponseDTO>> response = urlService.getPersonByLastName("Doe");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PersonResponseDTO> responseList = response.getBody();
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("John", responseList.get(0).getFirstName());
        assertEquals("Doe", responseList.get(0).getLastName());
        assertEquals("Jane", responseList.get(1).getFirstName());
    }

    @Test
    void testGetEmailByCity() {
        List<String> result = urlService.getEmailByCity("City");
        assertEquals(2, result.size());
        assertTrue(result.contains("john@test.com"));
        assertTrue(result.contains("jane@test.com"));
    }

    @Test
    void testGetEmailByCityNoPerson() {
        when(personRepository.getAllPersons()).thenReturn(List.of());

        List<String> result = urlService.getEmailByCity("City");
        assertTrue(result.isEmpty());
    }
}
