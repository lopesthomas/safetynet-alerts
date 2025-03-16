package com.safetynet.safetynet_alerts.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloodResponseDTO {
    @JsonProperty("stationNumber")
    private String station;
    private String address;
    private List<FireResponseDTO> residents;
}
