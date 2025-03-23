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

@RestController
public class URLController {
    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;
    @Autowired
    private URLService urlService;

    private static Logger logger = LoggerFactory.getLogger(URLController.class);

    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChildrenAtAdress(@RequestParam String address) {
        logger.debug("Request: GET /childAlert?address=<address>" , address);
        return personService.getChildrenAtAddress(address);
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumberByFireStation(@RequestParam int stationNumber) {
        logger.debug("Request: GET /phoneAlert?firestation=<firestation_number>" , stationNumber);
        return fireStationService.getPhoneNumberByStationNumber(stationNumber);
    }

    @GetMapping("/fire")
    public ResponseEntity<List<PersonResponseDTO>> getPersonsByAddress(@RequestParam String address) {
        logger.debug("Request: GET /fire?address=<address>", address);
        return urlService.getPersonsByAddress(address);
    }

    @GetMapping("flood/stations")
    public List<FloodResponseDTO> getPersonsByFireStationAdress(@RequestParam List<Integer> stations) {
        logger.debug("Request: GET /flood/stations?stations=<station_number>", stations);
        return urlService.getHouseholdsByFireStations(stations);
    }

    @GetMapping("/personInfolastName={lastName}")
    public ResponseEntity<List<PersonResponseDTO>> getPersonInfoByLastName(@PathVariable String lastName) {
        logger.debug("Request: GET /personInfolastName=<lastName>", lastName);
        return urlService.getPersonByLastName(lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailByCity(@RequestParam String city) {
        logger.debug("Request: GET /communityEmail?city=<city>", city);
        return urlService.getEmailByCity(city);
    }


}
