package com.tripycco.tripyccobackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "agency_profile")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgencyProfile {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "business_license_url")
    private String businessLicenseUrl;

    @Column(name = "verification_notes")
    private String verificationNotes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}