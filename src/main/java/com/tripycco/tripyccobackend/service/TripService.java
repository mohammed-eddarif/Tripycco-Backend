package com.tripycco.tripyccobackend.service;

import com.tripycco.tripyccobackend.model.AgencyProfile;
import com.tripycco.tripyccobackend.model.Trip;
import com.tripycco.tripyccobackend.repository.AgencyProfileRepository;
import com.tripycco.tripyccobackend.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private AgencyProfileRepository agencyProfileRepository;

    public void createTrip(Trip trip, UUID agencyId) {
        AgencyProfile agencyProfile = agencyProfileRepository.findById(agencyId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found"));

        trip.setAgencyProfile(agencyProfile);
        tripRepository.save(trip);
    }

    public List<Trip> getAllTripsByAgencyId(UUID agencyId) {
        return tripRepository.findAllByAgencyProfile_agencyId(agencyId);
    }

    public Trip updateTrip(Long id, Trip updatedTrip) {
        Optional<Trip> existingTripOpt = tripRepository.findById(id);
        if (existingTripOpt.isPresent()) {
            Trip existingTrip = existingTripOpt.get();
            existingTrip.setTripPictureUrls(updatedTrip.getTripPictureUrls());
            existingTrip.setTitle(updatedTrip.getTitle());
            existingTrip.setDescription(updatedTrip.getDescription());
            existingTrip.setMaxPlaces(updatedTrip.getMaxPlaces());
            existingTrip.setStartDate(updatedTrip.getStartDate());
            existingTrip.setEndDate(updatedTrip.getEndDate());
            existingTrip.setPrice(updatedTrip.getPrice());
            existingTrip.setStatus(updatedTrip.getStatus());
            existingTrip.setDestinations(updatedTrip.getDestinations());
            return tripRepository.save(existingTrip);
        }
        return null;
    }

    public List<Trip> deleteTrip(Long id) {
        Optional<Trip> tripOpt = tripRepository.findById(id);
        if (tripOpt.isPresent()) {
            Trip trip = tripOpt.get();
            tripRepository.delete(trip);
            return tripRepository.findAll();
        }
        return null;
    }
}
