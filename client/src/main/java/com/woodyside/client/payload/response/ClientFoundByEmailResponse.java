package com.woodyside.client.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
public class ClientFoundByEmailResponse {

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    private String email;

    @JsonProperty(value = "is_fraudster")
    private Boolean isFraudster;

    private Boolean success;

    private String timestamp;

}
