package com.tripycco.tripyccobackend.controller;

import com.tripycco.tripyccobackend.dto.ResponseObject;
import com.tripycco.tripyccobackend.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.tripycco.tripyccobackend.model.Trip;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trips")
public class TripController {


    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/{agencyId}")
    public ResponseEntity<ResponseObject<List<Trip>>> createTrip(@RequestBody Trip trip, @PathVariable UUID agencyId) {
        tripService.createTrip(trip, agencyId);
        List<Trip> trips = tripService.getAllTripsByAgencyId(agencyId);

        ResponseObject<List<Trip>> responseObject = new ResponseObject<>(
                true,
                "Trip created successfully",
                trips
        );

        return ResponseEntity.ok(responseObject);
    }


    @GetMapping("/allTripsByAgency/{agencyId}")
    public ResponseEntity<List<Trip>> getAllTripsByAgencyId(@PathVariable UUID agencyId) {
        List<Trip> trips = tripService.getAllTripsByAgencyId(agencyId);
        return ResponseEntity.ok(trips);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<Trip>> updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        Trip updated = tripService.updateTrip(id, trip);
        if (updated == null) {
            ResponseObject<Trip> responseObject = new ResponseObject<>(
                    false,
                    "Trip update failed",
                    trip
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }

        ResponseObject<Trip> responseObject = new ResponseObject<>(
                true,
                "Trip updated successfully",
                updated
        );
        return ResponseEntity.ok(responseObject);
    }

    @DeleteMapping("/{id}/{agencyId}")
    public ResponseEntity<ResponseObject<List<Trip>>> deleteTrip(@PathVariable Long id, @PathVariable UUID agencyId) {
        // TODO: extract the agencyId from the connected user
        //  (Get the authenticated user's keycloakId, then Fetch the agencyId using the keycloakId)

        tripService.deleteTrip(id);
        List<Trip> trips = tripService.getAllTripsByAgencyId(agencyId);

        ResponseObject<List<Trip>> responseObject = new ResponseObject<>(
                true,
                "Trip created successfully",
                trips
        );

        return ResponseEntity.ok(responseObject);
    }
}
