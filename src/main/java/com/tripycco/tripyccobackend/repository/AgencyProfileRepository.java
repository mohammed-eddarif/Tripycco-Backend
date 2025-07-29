package com.tripycco.tripyccobackend.repository;

import com.tripycco.tripyccobackend.model.AgencyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgencyProfileRepository extends JpaRepository<AgencyProfile, UUID> {
    // Basic CRUD methods are automatically provided by JpaRepository
    // You can add custom query methods here if needed
}
