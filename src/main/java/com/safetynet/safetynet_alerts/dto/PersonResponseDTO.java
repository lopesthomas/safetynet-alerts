package com.safetynet.safetynet_alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    private Integer fireStationCoveredPersons;
}
