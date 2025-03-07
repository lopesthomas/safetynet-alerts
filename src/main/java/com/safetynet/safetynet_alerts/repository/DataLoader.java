package com.safetynet.safetynet_alerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.model.Person;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Repository
@Getter
public class DataLoader {
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;

    @PostConstruct
    public void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/data.json");
            DataLoader data = objectMapper.readValue(file, DataLoader.class);
            this.persons = data.getPersons();
            this.firestations = data.getFirestations();
            this.medicalrecords = data.getMedicalrecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/data.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
