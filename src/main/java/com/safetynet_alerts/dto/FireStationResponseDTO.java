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
    @JsonProperty("persons_covered")
    private List<Person> coveredPersons;

    @JsonProperty("number_of_adults")
    private int adultCount;

    @JsonProperty("number_of_children")
    private int childCount;
}
