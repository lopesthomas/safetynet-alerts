package com.safetynet.safetynet_alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynet_alerts.dto.FireStationResponseDTO;
import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.service.FireStationService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/firestation")
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;


    @GetMapping
    public FireStationResponseDTO getCoveredPersons(@RequestParam int stationNumber) {
        //TODO: process POST request
        return fireStationService.getPersonsCoveredByStation(stationNumber);
    }

    @GetMapping("/all")
    public List<FireStation> getFireStations() {
        //TODO: process POST request
        return fireStationService.getFireStations();
    }

    @PostMapping
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation) {
        //TODO: process POST request
        FireStation createdFireStation = fireStationService.addFireStation(fireStation);
        return ResponseEntity.ok(createdFireStation);
    }

    @PutMapping
    public ResponseEntity<FireStation> updateFireStation(@RequestBody FireStation firestation) {
        //TODO: process PUT request
        FireStation updatedFireStation = fireStationService.updateFireStation(firestation);
        if (updatedFireStation != null) {
            return ResponseEntity.ok(updatedFireStation);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable String address) {
        boolean deleted = fireStationService.deleteFireStation(address);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
