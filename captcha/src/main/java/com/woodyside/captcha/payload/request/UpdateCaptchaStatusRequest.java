package com.woodyside.captcha.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCaptchaStatusRequest {

    @JsonProperty(value = "captcha_id")
    private String captchaId;

    @JsonProperty(value = "entered_text")
    private String enteredText;

    @JsonProperty(value = "validated_result")
    private Boolean validatedResult;
}
