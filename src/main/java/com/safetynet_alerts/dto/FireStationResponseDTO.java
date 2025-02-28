package com.safetynet_alerts.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.safetynet_alerts.model.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationResponseDTO {
    @JsonProperty("personsCovered")
    private List<Person> coveredPersons;

    @JsonProperty("numberOfAdults")
    private int adultCount;

    @JsonProperty("numberOfChildren")
    private int childCount;
}
