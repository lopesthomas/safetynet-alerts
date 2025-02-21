package com.safetynet.safetynet_alerts.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.safetynet.safetynet_alerts.model.FireStation;

@Repository
public class FireStationRepository {
        private final DataLoader dataLoader;

    public FireStationRepository(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public List<FireStation> getAllFireStations() {
        return dataLoader.getFirestations();
    }

    public List<FireStation> findByStationNumber(int stationNumber ) {
        return dataLoader.getFirestations().stream()
                .filter(f -> f.getStation() == stationNumber)
                .collect(Collectors.toList());
    }

}
