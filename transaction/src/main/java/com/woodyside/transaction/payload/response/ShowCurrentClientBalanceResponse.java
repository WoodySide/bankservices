package com.woodyside.transaction.payload.response;

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
public class ShowCurrentClientBalanceResponse {

    @JsonProperty(value = "current_balance")
    private BigDecimal currentBalance;

    private String date;

}
