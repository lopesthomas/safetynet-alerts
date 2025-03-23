package com.safetynet.safetynet_alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynet_alerts.dto.ChildAlertDTO;
import com.safetynet.safetynet_alerts.dto.PersonResponseDTO;
import com.safetynet.safetynet_alerts.dto.FloodResponseDTO;
import com.safetynet.safetynet_alerts.service.FireStationService;
import com.safetynet.safetynet_alerts.service.PersonService;
import com.safetynet.safetynet_alerts.service.URLService;

/**
 * REST controller for handling various URL endpoints related to alerts and information retrieval
 */
@RestController
public class URLController {
    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;
    @Autowired
    private URLService urlService;

    private static Logger logger = LoggerFactory.getLogger(URLController.class);

    /**
     * Retrieves a list of children at a specific address
     *
     * @param address the address to search for children
     * @return a list of ChildAlertDTO containing information about the children
     */
    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChildrenAtAdress(@RequestParam String address) {
        logger.debug("Request: GET /childAlert?address=<address>" , address);
        return personService.getChildrenAtAddress(address);
    }

    /**
     * Retrieves a list of phone numbers associated with a specific fire station address
     *
     * @param stationNumber the number of the fire station
     * @return a list of phone numbers
     */
    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumberByFireStation(@RequestParam int stationNumber) {
        logger.debug("Request: GET /phoneAlert?firestation=<firestation_number>" , stationNumber);
        return fireStationService.getPhoneNumberByStationNumber(stationNumber);
    }

    /**
     * Retrieves a list of persons at a specific address
     *
     * @param address the address to search for persons
     * @return a ResponseEntity containing a list of PersonResponseDTO
     */
    @GetMapping("/fire")
    public ResponseEntity<List<PersonResponseDTO>> getPersonsByAddress(@RequestParam String address) {
        logger.debug("Request: GET /fire?address=<address>", address);
        return urlService.getPersonsByAddress(address);
    }

    /**
     * Retrieves a list of households covered by specific fire stations
     *
     * @param stations a list of station numbers
     * @return a list of FloodResponseDTO containing information about the households
     */
    @GetMapping("flood/stations")
    public List<FloodResponseDTO> getPersonsByFireStationAdress(@RequestParam List<Integer> stations) {
        logger.debug("Request: GET /flood/stations?stations=<a list of station_number>", stations);
        return urlService.getHouseholdsByFireStations(stations);
    }

    /**
     * Retrieves information about persons by their last name
     *
     * @param lastName the last name to search for persons
     * @return a ResponseEntity containing a list of PersonResponseDTO
     */
    @GetMapping("/personInfolastName={lastName}")
    public ResponseEntity<List<PersonResponseDTO>> getPersonInfoByLastName(@PathVariable String lastName) {
        logger.debug("Request: GET /personInfolastName=<lastName>", lastName);
        return urlService.getPersonByLastName(lastName);
    }

    /**
     * Retrieves a list of email addresses for persons in a specific city.
     *
     * @param city the city to search for email addresses
     * @return a list of email addresses
     */
    @GetMapping("/communityEmail")
    public List<String> getEmailByCity(@RequestParam String city) {
        logger.debug("Request: GET /communityEmail?city=<city>", city);
        return urlService.getEmailByCity(city);
    }


}
