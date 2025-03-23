package com.safetynet.safetynet_alerts.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.PersonRepository;
import com.safetynet.safetynet_alerts.service.PersonService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * REST controller for managing person operations
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    /**
     * Retrieves the list of all persons
     *
     * @return a list of all persons
     */
    @GetMapping("/all")
    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }
    

    /**
     * Creates a new person
     *
     * @param person the person to create
     * @return a ResponseEntity containing the created person
     */
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        logger.debug("Request: POST /person", person);
        return ResponseEntity.ok(createdPerson);
    }

    /**
     * Updates an existing person
     *
     * @param person the person to update
     * @return a ResponseEntity containing the updated person
     */
    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(person);
        logger.debug("Request: PUT /person", person);
        return ResponseEntity.ok(updatedPerson);
    }

    /**
     * Deletes a person by first name and last name
     *
     * @param firstName the first name of the person to delete
     * @param lastName the last name of the person to delete
     * @return a void ResponseEntity indicating the success of the deletion
     */
    @DeleteMapping
    public ResponseEntity<Void> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);
        logger.debug("Request: DELETE /person?firstName=<firstName>&lastName=<lastName>", firstName, lastName);
        return ResponseEntity.noContent().build();
    }
    
}
