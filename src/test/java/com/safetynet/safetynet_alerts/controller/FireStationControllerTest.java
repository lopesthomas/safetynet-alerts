package com.safetynet.safetynet_alerts.controller;

import com.safetynet.safetynet_alerts.dto.FireStationResponseDTO;
import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.service.FireStationService;
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

class FireStationControllerTest {

    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private FireStationController fireStationController;

    private FireStation fireStation;
    private List<FireStation> fireStations;
    private FireStationResponseDTO fireStationResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fireStation = new FireStation("Address1", 1);
        fireStations = Arrays.asList(fireStation, new FireStation("Address2", 2));
        fireStationResponseDTO = new FireStationResponseDTO(Arrays.asList(new Person("John", "Doe", "Address1", "City", "12345", "123-456-7890", "john.doe@example.com")), 1, 0);
    }

    @Test
    void testGetCoveredPersons() {
        when(fireStationService.getPersonsCoveredByStation(1)).thenReturn(fireStationResponseDTO);
        FireStationResponseDTO result = fireStationController.getCoveredPersons(1);
        assertEquals(fireStationResponseDTO, result);
    }

    @Test
    void testGetFireStations() {
        when(fireStationService.getFireStations()).thenReturn(fireStations);
        List<FireStation> result = fireStationController.getFireStations();
        assertEquals(fireStations, result);
    }

    @Test
    void testCreateFireStation() {
        when(fireStationService.addFireStation(fireStation)).thenReturn(fireStation);
        ResponseEntity<FireStation> response = fireStationController.createFireStation(fireStation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fireStation, response.getBody());
    }

    @Test
    void testUpdateFireStation() {
        when(fireStationService.updateFireStation(fireStation)).thenReturn(fireStation);
        ResponseEntity<FireStation> response = fireStationController.updateFireStation(fireStation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fireStation, response.getBody());
    }

    @Test
    void testUpdateFireStationNotFound() {
        when(fireStationService.updateFireStation(fireStation)).thenReturn(null);
        ResponseEntity<FireStation> response = fireStationController.updateFireStation(fireStation);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteFireStation() {
        when(fireStationService.deleteFireStation("Address1")).thenReturn(true);
        ResponseEntity<Void> response = fireStationController.deleteFireStation("Address1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteFireStationNotFound() {
        when(fireStationService.deleteFireStation("Address1")).thenReturn(false);
        ResponseEntity<Void> response = fireStationController.deleteFireStation("Address1");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
