package com.tripycco.tripyccobackend.model;

import com.tripycco.tripyccobackend.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "travel_date")
    private LocalDate travelDate;

    @Column(name = "places_reserved")
    private Integer placesReserved = 1;

    private BigDecimal amount;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traveler_profile_id", nullable = false)
    private TravelerProfile travelerProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @PrePersist
    protected void onCreate() {
        bookingDate = LocalDateTime.now();
    }
}
