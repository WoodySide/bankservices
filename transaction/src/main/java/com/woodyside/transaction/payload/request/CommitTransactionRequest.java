package com.woodyside.transaction.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class WithdrawMoneyRequest {

    @JsonProperty(value = "client_name")
    private String clientUsername;

    @JsonProperty(value = "current_balance")
    @Min(value = 0, message = "Your current balance can not be less than 0")
    private BigDecimal moneyToWithdraw;
}
