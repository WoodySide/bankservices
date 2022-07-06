package com.woodyside.fraud.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IsFraudulentClientResponse {

    private String info;

    private String timestamp;
}
