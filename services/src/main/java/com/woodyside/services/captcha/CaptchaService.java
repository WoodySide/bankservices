package com.woodyside.services.captcha;


import com.woodyside.services.captcha.payload.response.GetCaptchaResponseBody;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "captcha",
        path = "api/v1/captcha"
)
public interface CaptchaService {

    @GetMapping(path = "/{captcha_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GetCaptchaResponseBody> findByCaptchaId(@PathVariable(value = "captcha_id") String captchaId);
}
