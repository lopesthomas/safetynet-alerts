package com.safetynet.safetynet_alerts.repository;

import com.safetynet.safetynet_alerts.model.FireStation;
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

class FireStationRepositoryTest {

    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private FireStationRepository fireStationRepository;

    private List<FireStation> fireStations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fireStations = new ArrayList<>();
        fireStations.add(new FireStation("Address1", 1));
        fireStations.add(new FireStation("Address2", 2));
        when(dataLoader.getFirestations()).thenReturn(fireStations);
    }

    @Test
    void testGetAllFireStations() {
        List<FireStation> result = fireStationRepository.getAllFireStations();
        assertEquals(2, result.size());
    }

    @Test
    void testFindByStationNumber() {
        List<FireStation> result = fireStationRepository.findByStationNumber(1);
        assertEquals(1, result.size());
        assertEquals("Address1", result.get(0).getAddress());
    }

    @Test
    void testAddFireStation() {
        FireStation newFireStation = new FireStation("Address3", 3);
        FireStation result = fireStationRepository.addFireStation(newFireStation);
        assertEquals(newFireStation, result);
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testUpdateFireStation() {
        FireStation updatedFireStation = new FireStation("Address1", 4);
        FireStation result = fireStationRepository.updateFireStation(updatedFireStation);
        assertEquals(updatedFireStation.getStation(), result.getStation());
        verify(dataLoader, times(1)).saveData();
    }

    @Test
    void testDeleteFireStation() {
        Optional<Boolean> result = fireStationRepository.deleteFireStation("Address1");
        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(dataLoader, times(1)).saveData();
    }
}
