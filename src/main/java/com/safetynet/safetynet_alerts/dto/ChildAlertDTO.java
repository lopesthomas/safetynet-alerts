package com.safetynet.safetynet_alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<String> housedMembers;
}
