package com.crabcodingtest.domain.signup.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Address {

    private String city;
    private String street;
    private String zipCode;
}
