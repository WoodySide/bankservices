package com.woodyside.services.captcha.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetCaptchaResponseBody {

    @JsonProperty(value = "validated_result")
    private Boolean validatedResult;

    private String email;

    private String timestamp;
}
