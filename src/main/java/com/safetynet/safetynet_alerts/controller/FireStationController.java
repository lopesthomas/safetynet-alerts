package com.safetynet.safetynet_alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.service.FireStationService;
import com.safetynet_alerts.dto.FireStationResponseDTO;

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

    @PostMapping
    public FireStation createFireStation(@RequestBody FireStation fireStation) {
        //TODO: process POST request
        
        return fireStation;
    }

    @PutMapping("/{id}")
    public FireStation updateFireStation(@PathVariable String id, @RequestBody FireStation firestation) {
        //TODO: process PUT request
        
        return firestation;
    }
    
    @DeleteMapping("/{id}")
    public void deleteFireStation(@PathVariable String id) {
        
    }
    
    

}
