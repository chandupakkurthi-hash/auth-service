package com.no_broker_application.Auth_Service.service;

import com.no_broker_application.Auth_Service.dto.AuthResponse;
import com.no_broker_application.Auth_Service.dto.GoogleLoginRequest;
import com.no_broker_application.Auth_Service.dto.UpdateUserRequest;
import com.no_broker_application.Auth_Service.entity.User;
import com.no_broker_application.Auth_Service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse handleGoogleLogin(GoogleLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(request.getEmail());
                    newUser.setName(request.getName());
                    newUser.setProfileImageUrl(request.getProfileImageUrl());
                    newUser.setRole("USER");
                    newUser.setIsSubscribed(false);
                    return userRepository.save(newUser);
                });

        String token = jwtService.generateToken(user.getEmail(), user.getUserId(), user.getRole());

        return new AuthResponse(
                token,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getRole(),
                user.getIsSubscribed(),
                user.getProfileImageUrl()
        );
    }

    public AuthResponse validateToken(String token) {
        if (!jwtService.isTokenValid(token)) {
            throw new RuntimeException("Invalid or expired JWT token");
        }

        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for token"));

        return new AuthResponse(
                token,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getRole(),
                user.getIsSubscribed(),
                user.getProfileImageUrl()
        );
    }

    public AuthResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getMobilePhone() != null) {
            user.setMobilePhone(request.getMobilePhone());
        }
        if (request.getProfileImageUrl() != null) {
            user.setProfileImageUrl(request.getProfileImageUrl());
        }

        user = userRepository.save(user);
        return new AuthResponse(
                null,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getRole(),
                user.getIsSubscribed(),
                user.getProfileImageUrl()
        );
    }

    public AuthResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return new AuthResponse(
                null,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getRole(),
                user.getIsSubscribed(),
                user.getProfileImageUrl()
        );
    }

    public AuthResponse updateSubscription(Long userId, boolean subscribed) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setIsSubscribed(subscribed);
        user = userRepository.save(user);
        return new AuthResponse(
                null,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getRole(),
                user.getIsSubscribed(),
                user.getProfileImageUrl()
        );
    }
}
