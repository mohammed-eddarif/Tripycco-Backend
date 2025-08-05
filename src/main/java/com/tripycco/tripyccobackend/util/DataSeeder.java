package com.tripycco.tripyccobackend.util;

import com.tripycco.tripyccobackend.enums.PaymentStatus;
import com.tripycco.tripyccobackend.enums.TripStatus;
import com.tripycco.tripyccobackend.enums.UserRole;
import com.tripycco.tripyccobackend.model.*;
import com.tripycco.tripyccobackend.repository.AppUserRepository;
import com.tripycco.tripyccobackend.repository.BookingRepository;
import com.tripycco.tripyccobackend.repository.TripReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final BookingRepository bookingRepository;
    private final TripReviewRepository tripReviewRepository;

    public DataSeeder(AppUserRepository appUserRepository, BookingRepository bookingRepository, TripReviewRepository tripReviewRepository) {
        this.appUserRepository = appUserRepository;
        this.bookingRepository = bookingRepository;
        this.tripReviewRepository = tripReviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (appUserRepository.count() > 0) {
            return;
        }
        System.out.println("DEV PROFILE: Seeding database with test data...");

        AppUser agencyUser = createAgencyUser();
        appUserRepository.save(agencyUser);

        AppUser travelerUser = createTravelerUser();
        appUserRepository.save(travelerUser);

        Trip tripToBook = agencyUser.getAgencyProfile().getTrips().stream().findFirst().orElse(null);
        if (tripToBook != null) {
            Booking booking = createBooking(travelerUser.getTravelerProfile(), tripToBook);
            bookingRepository.save(booking);

            TripReview review = createTripReview(travelerUser.getTravelerProfile(), tripToBook);
            tripReviewRepository.save(review);
        }

        System.out.println("---");
        System.out.println("DEV MODE: TEST DATA CREATED");
        System.out.println("Test Agency User ID: " + agencyUser.getId());
        System.out.println("Test Traveler User ID: " + travelerUser.getId());
        if (tripToBook != null) {
            System.out.println("Test Trip ID: " + tripToBook.getId());
        }
        System.out.println("---");
    }

    private AppUser createAgencyUser() {
        AppUser agencyUser = new AppUser();
        agencyUser.setKeycloakId("fake-agency-keycloak-id-123");
        agencyUser.setRole(UserRole.AGENCY);
        agencyUser.setFirstName("Atlas");
        agencyUser.setLastName("Adventures");
        agencyUser.setEmail("contact@atlasadventures.test");
        agencyUser.setPhoneNumber("555-0101");

        AgencyProfile agencyProfile = new AgencyProfile();
        agencyProfile.setAgencyName("Atlas Adventures");
        agencyProfile.setIsVerified(true);
        agencyProfile.setBio("Your number one source for adventures in Morocco!");

        agencyProfile.setUser(agencyUser);
        agencyUser.setAgencyProfile(agencyProfile);

        Trip trip1 = new Trip();
        trip1.setTitle("Hike to Toubkal Summit");
        trip1.setDescription("A challenging but rewarding 3-day trek to the highest peak in North Africa.");
        trip1.setMaxPlaces(12);
        trip1.setPrice(new BigDecimal("1500.00"));
        trip1.setStartDate(LocalDate.now().plusDays(30));
        trip1.setEndDate(LocalDate.now().plusDays(32));
        trip1.setStatus(TripStatus.AVAILABLE);
        trip1.setDestinations(List.of("Marrakech", "Imlil", "Toubkal National Park"));

        Trip trip2 = new Trip();
        trip2.setTitle("Sahara Stars & Desert Sands");
        trip2.setDescription("An unforgettable 4-day journey into the heart of the Sahara desert.");
        trip2.setMaxPlaces(8);
        trip2.setPrice(new BigDecimal("2200.00"));
        trip2.setStartDate(LocalDate.now().plusDays(60));
        trip2.setEndDate(LocalDate.now().plusDays(63));
        trip2.setStatus(TripStatus.AVAILABLE);
        trip2.setDestinations(List.of("Ouarzazate", "Merzouga", "Erg Chebbi"));

        agencyProfile.addTrip(trip1);
        agencyProfile.addTrip(trip2);

        return agencyUser;
    }

    private AppUser createTravelerUser() {
        AppUser travelerUser = new AppUser();
        travelerUser.setKeycloakId("fake-traveler-keycloak-id-456");
        travelerUser.setRole(UserRole.TRAVELER);
        travelerUser.setFirstName("John");
        travelerUser.setLastName("Doe");
        travelerUser.setEmail("john.doe@traveler.test");
        travelerUser.setPhoneNumber("555-0202");

        TravelerProfile travelerProfile = new TravelerProfile();
        travelerProfile.setBio("Avid hiker and photographer looking for the next great adventure.");
        travelerProfile.setPreferences("Mountains, Nature, Authentic Food");

        travelerProfile.setUser(travelerUser);
        travelerUser.setTravelerProfile(travelerProfile);

        return travelerUser;
    }

    private Booking createBooking(TravelerProfile traveler, Trip trip) {
        Booking booking = new Booking();
        booking.setTravelerProfile(traveler);
        booking.setTrip(trip);
        booking.setPlacesReserved(2);
        booking.setAmount(trip.getPrice().multiply(new BigDecimal("2")));
        booking.setPaymentStatus(PaymentStatus.PAID);
        booking.setTravelDate(trip.getStartDate());
        return booking;
    }

    private TripReview createTripReview(TravelerProfile traveler, Trip trip) {
        TripReview review = new TripReview();
        review.setTravelerProfile(traveler);
        review.setTrip(trip);
        review.setRating(5);
        review.setComment("Absolutely incredible experience! The guides at Atlas Adventures were professional and the views were breathtaking. Highly recommended!");
        return review;
    }
}