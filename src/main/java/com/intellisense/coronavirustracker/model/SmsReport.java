package com.intellisense.coronavirustracker.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class SmsReport {

    @NotBlank
    private final String phoneNumber; // destination

    @NotBlank
    private final String state; // state where the virus is suspected

    @NotBlank
    private final String country; // address where the virus is suspected

    @NotBlank
    private final String address; // address where the virus is suspected

    @NotBlank
    private final String message;

    public SmsReport(@NotBlank String phoneNumber, @NotBlank String state, @NotBlank String country, @NotBlank String address, @NotBlank String message) {
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.country = country;
        this.address = address;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getMessage() {
        return message;
    }
}
