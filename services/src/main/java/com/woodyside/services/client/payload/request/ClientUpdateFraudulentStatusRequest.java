package com.woodyside.services.client.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateFraudulentStatusRequest {

    @JsonProperty(value = "is_fraudster")
    private Boolean isFraudster;

    private String email;
}
