package com.tripycco.tripyccobackend.controller;

import com.tripycco.tripyccobackend.dto.ResponseObject;
import com.tripycco.tripyccobackend.dto.UserRegistrationRequest;
import com.tripycco.tripyccobackend.model.AppUser;
import com.tripycco.tripyccobackend.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject<AppUser>> registerUser(@RequestBody UserRegistrationRequest request) {
        // Validate that keycloakId is provided from frontend
        if (request.getKeycloakId() == null) {
            return ResponseEntity.badRequest().body(
                    new ResponseObject<>(false, "Keycloak ID is required", null)
            );
        }

        AppUser user = registrationService.registerUser(request);

        ResponseObject<AppUser> responseObject = new ResponseObject<>(
                true,
                "User registered successfully",
                user
        );

        return ResponseEntity.ok(responseObject);
    }
}
