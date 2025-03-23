package com.safetynet.safetynet_alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


/**
 * REST controller for managing fire station operations.
 */
@RestController
@RequestMapping("/firestation")
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;

    private static Logger logger = LoggerFactory.getLogger(FireStationController.class);

    /**
     * Retrieves the list of persons covered by a specific fire station
     *
     * @param stationNumber the number of the fire station
     * @return a DTO containing the list of persons covered by the fire station
     */
    @GetMapping
    public FireStationResponseDTO getCoveredPersons(@RequestParam int stationNumber) {
        logger.debug("Request: GET /firestation?stationNumber=<station_number>", stationNumber);
        return fireStationService.getPersonsCoveredByStation(stationNumber);
    }

    /**
     * Retrieves the list of all fire stations
     *
     * @return a list of all fire stations
     */
    @GetMapping("/all")
    public List<FireStation> getFireStations() {
        return fireStationService.getFireStations();
    }

    /**
     * Creates a new fire station
     *
     * @param fireStation the fire station to create
     * @return a ResponseEntity containing the created fire station
     */
    @PostMapping
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation) {
        FireStation createdFireStation = fireStationService.addFireStation(fireStation);
        logger.debug("Request: POST /firestation", fireStation);
        return ResponseEntity.ok(createdFireStation);
    }

    /**
     * Updates an existing fire station
     *
     * @param fireStation the fire station to update
     * @return a ResponseEntity containing the updated fire station, or not found if the fire station does not exist
     */
    @PutMapping
    public ResponseEntity<FireStation> updateFireStation(@RequestBody FireStation firestation) {
        FireStation updatedFireStation = fireStationService.updateFireStation(firestation);
        if (updatedFireStation != null) {
            return ResponseEntity.ok(updatedFireStation);
        }
        logger.debug("Request: PUT /firestation", firestation);
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Deletes a fire station by address
     *
     * @param address the address of the fire station to delete
     * @return a ResponseEntity indicating the success of the deletion
     */
    @DeleteMapping("/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable String address) {
        boolean deleted = fireStationService.deleteFireStation(address);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        logger.debug("Request: DELETE /firestation?address=<address>", address);
        return ResponseEntity.notFound().build();
    }

}
