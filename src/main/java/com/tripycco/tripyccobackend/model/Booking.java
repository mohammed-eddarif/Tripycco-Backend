package com.tripycco.tripyccobackend.model;

import com.tripycco.tripyccobackend.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "traveler_id")
    private UUID travelerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traveler_id", insertable = false, updatable = false)
    private AppUser traveler;

    @NotNull
    @Column(name = "trip_id")
    private Long tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", insertable = false, updatable = false)
    private Trip trip;

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

    @PrePersist
    protected void onCreate() {
        bookingDate = LocalDateTime.now();
    }
}
