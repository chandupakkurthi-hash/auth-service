package com.no_broker_application.Auth_Service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String mobilePhone;
    private String profileImageUrl;
}

