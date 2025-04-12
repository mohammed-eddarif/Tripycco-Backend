package com.tripycco.tripyccobackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "traveler")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Traveler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 30)
    private String username;

    @Column(unique = true)
    @Email
    private String email;

    private String address;

    // Keycloak user ID to link this traveler with Keycloak user
    @Column(unique = true)
    private String keycloakId;

    private String phoneNumber;

    private String profilePicture;

}
