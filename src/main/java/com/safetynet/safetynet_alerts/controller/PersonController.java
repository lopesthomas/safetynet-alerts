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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/all")
    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }
    

    @PostMapping
    public ResponseEntity<String> postMethodName(@RequestBody Person person) {
        //TODO: process POST request
        personService.addPerson(person);
        return ResponseEntity.ok("Created Person");
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        //TODO: process PUT request
        boolean update = personService.updatePerson(person);
        if(update){
            return ResponseEntity.ok("Updated Person");
        }
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        boolean delete = personService.deletePerson(firstName, lastName);
        if(delete){
            return ResponseEntity.ok("Deleted Person");
        }
        return ResponseEntity.notFound().build();
    }
    
}
