package com.woodyside.client.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientUpdateFraudulentStatusResponse {

    @JsonProperty(value = "is_fraudster")
    private Boolean isFraudster;

    private String timestamp;
}
