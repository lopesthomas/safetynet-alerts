package com.safetynet.safetynet_alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynet_alerts.dto.ChildAlertDTO;
import com.safetynet.safetynet_alerts.dto.FireResponseDTO;
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
        logger.info("Request: GET /childAlert?address=<address>" , address);
        return personService.getChildrenAtAddress(address);
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumberByFireStation(@RequestParam int stationNumber) {
        logger.info("Request: GET /phoneAlert?firestation=<firestation_number>" , stationNumber);
        return fireStationService.getPhoneNumberByStationNumber(stationNumber);
    }

    @GetMapping("/fire")
    public ResponseEntity<List<FireResponseDTO>> getPersonsByAddress(@RequestParam String address) {
        logger.info("Request: GET /fire?address=<address>", address);
        return urlService.getPersonsByAddress(address);
    }

}
