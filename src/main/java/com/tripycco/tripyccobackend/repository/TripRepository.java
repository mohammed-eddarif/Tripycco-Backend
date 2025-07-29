package com.tripycco.tripyccobackend.repository;

import com.tripycco.tripyccobackend.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAllByAgencyProfile_agencyId(UUID agencyId);

}
