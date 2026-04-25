package com.no_broker_application.Auth_Service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String mobilePhone;

    private String role = "USER";

    private Boolean isSubscribed = false;

    private String profileImageUrl;
}
