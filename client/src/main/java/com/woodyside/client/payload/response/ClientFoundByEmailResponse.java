package com.woodyside.client.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientFoundByEmailResponse {

    private Integer id;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    private String email;

    @JsonProperty(value = "current_balance")
    private BigDecimal currentBalance;

    @JsonProperty(value = "is_fraudster")
    private Boolean isFraudster;

    private Boolean success;

    private String timestamp;

}
