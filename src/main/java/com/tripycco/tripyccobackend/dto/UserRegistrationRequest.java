package com.tripycco.tripyccobackend.dto;

import com.tripycco.tripyccobackend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    private String keycloakId;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // Profile fields
    private String bio;
    private String profilePicture;

    // Traveler-specific fields
    private String preferences;

    // Agency-specific fields
    private String agencyName;
}