package com.safetynet.safetynet_alerts.repository;

import java.util.List;
import java.util.Optional;
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
        boolean createdFireStation = dataLoader.getFirestations().add(fireStation);
        if (createdFireStation) {
            dataLoader.saveData();
        }
        return fireStation;
    }

    public FireStation updateFireStation(FireStation fireStation){
        for (FireStation fs : dataLoader.getFirestations()) {
            if (fs.getAddress().equals(fireStation.getAddress())){
                fs.setStation(fireStation.getStation());
                dataLoader.saveData();
                return fs;
            }
        }
        return null;
    }

    public Optional<Boolean> deleteFireStation(String address) {
        boolean deletedFireStation = dataLoader.getFirestations().removeIf(fs -> fs.getAddress().equals(address));
        if (deletedFireStation) {
            dataLoader.saveData();
            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }

}
