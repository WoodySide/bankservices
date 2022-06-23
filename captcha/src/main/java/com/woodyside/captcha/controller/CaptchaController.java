package com.woodyside.captcha.controller;

import com.woodyside.captcha.payload.request.GenCaptchaRequestBody;
import com.woodyside.captcha.payload.response.GenCaptchaResponseBody;
import com.woodyside.captcha.service.CaptchaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "api/v1/captcha")
@AllArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @PostMapping(path = "/genCaptcha", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenCaptchaResponseBody> genCaptcha(@RequestBody GenCaptchaRequestBody captchaRequestBody) {
        return ResponseEntity.ok(captchaService.genCaptchaId(captchaRequestBody));
    }

    @GetMapping(path = "/getCaptcha/{captcha_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<String> getCaptcha(@PathVariable(value = "captcha_id") String captcha_id,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(captchaService.drawCaptcha(captcha_id,request,response));
    }
}
