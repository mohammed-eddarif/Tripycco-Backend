package com.tripycco.tripyccobackend.repository;

import com.tripycco.tripyccobackend.model.TripReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripReviewRepository extends JpaRepository<TripReview, UUID> {
}
