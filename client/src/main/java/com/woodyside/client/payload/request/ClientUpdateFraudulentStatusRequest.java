package com.woodyside.client.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientUpdateFraudulentStatusRequest {

    @JsonProperty(value = "is_fraudster")
    private Boolean isFraudster;

    private String email;
}
