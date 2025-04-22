package com.tripycco.tripyccobackend.model;

import com.tripycco.tripyccobackend.enums.TripStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "trip")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_profile", nullable = false)
    private AgencyProfile agencyProfile;

    @ElementCollection
    @CollectionTable(
            name = "trip_images",
            joinColumns = @JoinColumn(name = "trip_id")
    )
    @Column(name = "image_url")
    @OrderColumn(name = "image_position")  // This maintains order in database
    private List<String> tripPictureUrls = new ArrayList<>();

    @NotNull
    private String title;

    private String description;

    @NotNull
    @Column(name = "max_places")
    private Integer maxPlaces;

    @Column(name = "current_places")
    private Integer currentPlaces = 0;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TripStatus status = TripStatus.PENDING;

    @ElementCollection
    @CollectionTable(
            name = "trip_destination",
            joinColumns = @JoinColumn(name = "trip_id")
    )
    @Column(name = "destination")
    @OrderColumn(name = "destination_position")
    private List<String> destinations = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripReview> reviews = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}