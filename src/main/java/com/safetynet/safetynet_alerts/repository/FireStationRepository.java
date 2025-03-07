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

    public FireStation addFireStation(FireStation fireStation) {
        dataLoader.getFirestations().add(fireStation);
        return fireStation;
    }

    public FireStation updateFireStation(FireStation fireStation){
        for (FireStation fs : dataLoader.getFirestations()) {
            if (fs.getAddress().equals(fireStation.getAddress())){
                fs.setStation(fireStation.getStation());
                return fs;
            }
        }
        return null;
    }

    public boolean deleteFireStation(String address) {
        return dataLoader.getFirestations().removeIf(fs -> fs.getAddress().equals(address));
    }

}
