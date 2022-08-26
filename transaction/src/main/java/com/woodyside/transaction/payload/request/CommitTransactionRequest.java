package com.woodyside.transaction.payload.request;

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
public class CommitTransactionRequest {

    @JsonProperty(value = "client_username")
    private String clientUsername;

    @JsonProperty(value = "money_to_operate")
    private BigDecimal moneyToOperate;
}
