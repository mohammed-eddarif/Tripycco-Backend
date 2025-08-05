package com.tripycco.tripyccobackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "agency_profile")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AgencyProfile {

    @Id
    @Column(name = "agency_id")
    private UUID agencyId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "agency_id")
    private AppUser user;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "business_license_url")
    private String businessLicenseUrl;

    // Reasons for approval or rejection verification
    @Column(name = "verification_notes")
    private String verificationNotes;

    private String bio;

    private String profilePicture;

    @OneToMany(mappedBy = "agencyProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Trip> trips = new HashSet<>();

    @OneToMany(mappedBy = "agencyProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgencyReview> reviews = new ArrayList<>();

    public void addTrip(Trip trip) {
        trips.add(trip);
        trip.setAgencyProfile(this);
    }

    public void removeTrip(Trip trip) {
        trips.remove(trip);
        trip.setAgencyProfile(null);
    }

}