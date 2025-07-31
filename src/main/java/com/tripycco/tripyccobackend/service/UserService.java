package com.tripycco.tripyccobackend.service;

import com.tripycco.tripyccobackend.enums.UserRole;
import com.tripycco.tripyccobackend.model.AppUser;
import com.tripycco.tripyccobackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AppUserRepository userRepository;

    public UserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser getCurrentUser(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String keycloakId = jwt.getSubject();

        return userRepository.findByKeycloakId(keycloakId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean hasRole(Authentication authentication, UserRole role) {
        AppUser user = getCurrentUser(authentication);
        return user.getRole() == role;
    }
}
