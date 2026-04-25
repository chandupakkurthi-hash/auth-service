package com.no_broker_application.Auth_Service.controller;

import com.no_broker_application.Auth_Service.dto.AuthResponse;
import com.no_broker_application.Auth_Service.dto.GoogleLoginRequest;
import com.no_broker_application.Auth_Service.dto.UpdateUserRequest;
import com.no_broker_application.Auth_Service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google-login")
    public ResponseEntity<AuthResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        AuthResponse response = authService.handleGoogleLogin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = authHeader.substring(7);
        AuthResponse response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<AuthResponse> updateUser(@PathVariable Long userId,
                                                   @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(authService.updateUser(userId, request));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<AuthResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(authService.getUserById(userId));
    }

    @PutMapping("/users/{userId}/subscription")
    public ResponseEntity<AuthResponse> updateSubscription(@PathVariable Long userId,
                                                           @RequestBody java.util.Map<String, Object> request) {
        Object v = request.get("isSubscribed");
        boolean subscribed = Boolean.TRUE.equals(v) || "true".equalsIgnoreCase(String.valueOf(v));
        return ResponseEntity.ok(authService.updateSubscription(userId, subscribed));
    }
}
