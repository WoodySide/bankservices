package com.woodyside.services.client.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateFraudulentStatusResponse {

    @JsonProperty(value = "is_fraudster")
    private Boolean isFraudster;

    private String timestamp;
}
