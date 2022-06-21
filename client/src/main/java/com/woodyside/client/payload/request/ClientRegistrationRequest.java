package com.woodyside.client.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ClientRegistrationRequest {

    @JsonProperty(value = "first_name", required = true)
    private String firstName;

    @JsonProperty(value = "last_name", required = true)
    private String lastName;

    @JsonProperty(required = true)
    private String email;

    private String phone;

    private String country;

    private String city;

    private String region;

    private String street;

    @JsonProperty(value = "zip_code")
    private String zipCode;
}
