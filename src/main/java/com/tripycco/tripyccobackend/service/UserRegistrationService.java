package com.tripycco.tripyccobackend.service;

import com.tripycco.tripyccobackend.dto.UserRegistrationRequest;
import com.tripycco.tripyccobackend.enums.UserRole;
import com.tripycco.tripyccobackend.model.AgencyProfile;
import com.tripycco.tripyccobackend.model.AppUser;
import com.tripycco.tripyccobackend.model.TravelerProfile;
import com.tripycco.tripyccobackend.repository.AgencyProfileRepository;
import com.tripycco.tripyccobackend.repository.AppUserRepository;
import com.tripycco.tripyccobackend.repository.TravelerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {


    private final AppUserRepository userRepository;

    private final TravelerProfileRepository travelerProfileRepository;

    private final AgencyProfileRepository agencyProfileRepository;

    public UserRegistrationService(AppUserRepository userRepository, TravelerProfileRepository travelerProfileRepository, AgencyProfileRepository agencyProfileRepository) {
        this.userRepository = userRepository;
        this.travelerProfileRepository = travelerProfileRepository;
        this.agencyProfileRepository = agencyProfileRepository;
    }

    @Transactional
    public AppUser registerUser(UserRegistrationRequest request) {
        // Create and save the user
        AppUser user = new AppUser();
        user.setKeycloakId(request.getKeycloakId());
        user.setRole(request.getRole());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        // Save user first to generate ID
        user = userRepository.save(user);

        // Create and associate profile based on role
        if (UserRole.TRAVELER.equals(request.getRole())) {
            TravelerProfile profile = new TravelerProfile();
            profile.setUser(user);
            profile.setBio(request.getBio());
            profile.setProfilePicture(request.getProfilePicture());
            profile.setPreferences(request.getPreferences());

            travelerProfileRepository.save(profile);
            user.setTravelerProfile(profile);
        } else if (UserRole.AGENCY.equals(request.getRole())) {
            AgencyProfile profile = new AgencyProfile();
            profile.setUser(user);
            profile.setAgencyName(request.getAgencyName());
            profile.setBio(request.getBio());
            profile.setProfilePicture(request.getProfilePicture());
            profile.setIsVerified(false);

            agencyProfileRepository.save(profile);
            user.setAgencyProfile(profile);
        }

        return user;
    }
}
