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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "trip")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "agency_id", nullable = false)
    private UUID agencyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", insertable = false, updatable = false)
    private AppUser agency;

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
    private Set<String> destinations = new HashSet<>();

    @OneToMany(mappedBy = "trip")
    private Set<Booking> bookings = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}