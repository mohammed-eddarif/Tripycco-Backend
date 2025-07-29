package com.tripycco.tripyccobackend.repository;

import com.tripycco.tripyccobackend.model.TravelerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TravelerProfileRepository extends JpaRepository<TravelerProfile, UUID> {
}
