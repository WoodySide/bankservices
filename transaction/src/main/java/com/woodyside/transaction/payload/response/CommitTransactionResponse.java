package com.woodyside.transaction.payload.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class WithdrawMoneyResponse {

    private BigDecimal currentBalance;

    private String info;

    private String date;
}
