package com.no_broker_application.Auth_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String name;
    private String email;
    private String mobilePhone;
    private String role;
    private Boolean isSubscribed;
    private String profileImageUrl;
}
