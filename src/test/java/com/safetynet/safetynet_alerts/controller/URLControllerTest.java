package com.safetynet.safetynet_alerts.controller;

import com.safetynet.safetynet_alerts.dto.ChildAlertDTO;
import com.safetynet.safetynet_alerts.dto.FireResponseDTO;
import com.safetynet.safetynet_alerts.dto.FloodResponseDTO;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.service.FireStationService;
import com.safetynet.safetynet_alerts.service.PersonService;
import com.safetynet.safetynet_alerts.service.URLService;
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

class URLControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @Mock
    private URLService urlService;

    @InjectMocks
    private URLController urlController;

    private List<ChildAlertDTO> childAlertDTOs;
    private List<String> phoneNumbers;
    private List<FireResponseDTO> fireResponseDTOs;
    private List<FloodResponseDTO> floodResponseDTOs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        childAlertDTOs = Arrays.asList(new ChildAlertDTO("John", "Doe", 10, List.of("Jane Doe")));
        phoneNumbers = Arrays.asList("123-456-7890");
        fireResponseDTOs = Arrays.asList();
        floodResponseDTOs = Arrays.asList();
    }

    @Test
    void testGetChildrenAtAddress() {
        when(personService.getChildrenAtAddress("Address1")).thenReturn(childAlertDTOs);
        List<ChildAlertDTO> result = urlController.getChildrenAtAdress("Address1");
        assertEquals(childAlertDTOs, result);
    }

    @Test
    void testGetPhoneNumberByFireStation() {
        when(fireStationService.getPhoneNumberByStationNumber(1)).thenReturn(phoneNumbers);
        List<String> result = urlController.getPhoneNumberByFireStation(1);
        assertEquals(phoneNumbers, result);
    }

    @Test
    void testGetPersonsByAddress() {
        when(urlService.getPersonsByAddress("Address1")).thenReturn(ResponseEntity.ok(fireResponseDTOs));
        ResponseEntity<List<FireResponseDTO>> response = urlController.getPersonsByAddress("Address1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fireResponseDTOs, response.getBody());
    }

    @Test
    void testGetPersonsByFireStationAddress() {
        when(urlService.getHouseholdsByFireStations(List.of(1))).thenReturn(floodResponseDTOs);
        List<FloodResponseDTO> result = urlController.getPersonsByFireStationAdress(List.of(1));
        assertEquals(floodResponseDTOs, result);
    }

    @Test
    void testGetPersonInfoByLastName() {
        when(urlService.getPersonByLastName("Doe")).thenReturn(ResponseEntity.ok(fireResponseDTOs));
        ResponseEntity<List<FireResponseDTO>> response = urlController.getPersonInfoByLastName("Doe");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fireResponseDTOs, response.getBody());
    }

    @Test
    void testGetEmailByCity() {
        when(urlService.getEmailByCity("City")).thenReturn(Arrays.asList("john.doe@example.com"));
        List<String> result = urlController.getEmailByCity("City");
        assertEquals(Arrays.asList("john.doe@example.com"), result);
    }
}
