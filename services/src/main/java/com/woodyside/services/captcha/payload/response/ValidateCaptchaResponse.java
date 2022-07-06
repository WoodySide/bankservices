package com.woodyside.services.captcha.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateCaptchaResponse {

    @JsonProperty(value = "validate_flag")
    private Boolean validateFlag;

    private String timestamp;
}
