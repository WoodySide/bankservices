package com.woodyside.client.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailInUseResponse {

    private Boolean emailInUse;

    private String timestamp;

    private Boolean success;
}
