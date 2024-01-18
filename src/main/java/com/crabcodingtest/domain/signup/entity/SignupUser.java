package com.crabcodingtest.domain.signup.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "signupUser")
@RequiredArgsConstructor
public class SignupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String userPassword;
    private String userMail;
}
