package com.tripycco.tripyccobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AgencyVerificationRequest {
    private UUID agencyId;
    private String businessLicenseUrl;
}
