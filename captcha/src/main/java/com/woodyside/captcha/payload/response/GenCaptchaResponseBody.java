package com.woodyside.captcha.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenCaptchaResponseBody {

    @JsonProperty(value = "captcha_id")
    private String captchaId;
}
