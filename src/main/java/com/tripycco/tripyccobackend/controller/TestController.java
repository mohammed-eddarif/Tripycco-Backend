package com.tripycco.tripyccobackend.controller;

import com.tripycco.tripyccobackend.dto.UserRegistrationRequest;
import com.tripycco.tripyccobackend.enums.UserRole;
import com.tripycco.tripyccobackend.model.AppUser;
import com.tripycco.tripyccobackend.repository.AppUserRepository;
import com.tripycco.tripyccobackend.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private UserRegistrationService registrationService;

    @Autowired
    private AppUserRepository userRepository;

    @PostMapping("/register-test-user")
    public ResponseEntity<?> createTestUser(@RequestBody UserRegistrationRequest request) {
        // Generate a mock Keycloak ID if not provided
        if (request.getKeycloakId() == null) {
            request.setKeycloakId("mock-" + UUID.randomUUID().toString());
        }

        AppUser user = registrationService.registerUser(request);
        return ResponseEntity.ok(Map.of(
                "message", "Test user created successfully",
                "userId", user.getId(),
                "keycloakId", user.getKeycloakId(),
                "role", user.getRole()
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable UUID id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/profile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    if (user.getRole() == UserRole.TRAVELER) {
                        return ResponseEntity.ok(user.getTravelerProfile());
                    } else if (user.getRole() == UserRole.AGENCY) {
                        return ResponseEntity.ok(user.getAgencyProfile());
                    }
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}