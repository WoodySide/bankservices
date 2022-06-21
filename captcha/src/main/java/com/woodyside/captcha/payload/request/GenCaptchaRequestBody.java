package com.woodyside.captcha.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class GenCaptchaRequestBody {

    @JsonProperty(value = "client_username")
    private String clientUsername;
}
