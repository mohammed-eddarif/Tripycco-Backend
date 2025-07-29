package com.tripycco.tripyccobackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "traveler_profile")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TravelerProfile {

    @Id
    @Column(name = "traveler_id")
    private UUID travelerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "traveler_id")
    private AppUser user;

    private String bio;

    private String profilePicture;

    private String preferences;

    @OneToMany(mappedBy = "travelerProfile", fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "travelerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripReview> tripReviews = new ArrayList<>();

    @OneToMany(mappedBy = "travelerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgencyReview> agencyReviews = new ArrayList<>();

}
