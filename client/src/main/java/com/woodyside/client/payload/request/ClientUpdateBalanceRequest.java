package com.woodyside.client.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateBalanceRequest {

    @JsonProperty(value = "current_sum")
    private BigDecimal currentSum;

    private String email;
}
