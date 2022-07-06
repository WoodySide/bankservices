package com.woodyside.captcha.controller;

import com.woodyside.captcha.payload.request.GenCaptchaRequestBody;
import com.woodyside.captcha.payload.response.GenCaptchaResponseBody;
import com.woodyside.captcha.payload.response.ValidateCaptchaResponse;
import com.woodyside.captcha.service.CaptchaService;
import com.woodyside.services.captcha.payload.response.GetCaptchaResponseBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "api/v1/captcha")
@AllArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @GetMapping(path = "/{captcha_id}")
    public ResponseEntity<GetCaptchaResponseBody> findByCaptchaId(@PathVariable(value = "captcha_id") String captchaId) {
        return ResponseEntity.ok(captchaService.findByCaptchaId(captchaId));
    }

    @PostMapping(path = "/genCaptcha", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenCaptchaResponseBody> genCaptcha(@RequestBody GenCaptchaRequestBody captchaRequestBody) {
        return ResponseEntity.ok(captchaService.genCaptchaId(captchaRequestBody));
    }

    @GetMapping(path = "/drawCaptcha/{captcha_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<String> drawCaptcha(@PathVariable(value = "captcha_id") String captchaId,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(captchaService.drawCaptcha(captchaId, request, response));
    }

    @GetMapping(path = "/validateCaptcha", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidateCaptchaResponse> validateCaptcha(@RequestParam(value = "captcha_id") String captchaId,
                                                                   @RequestParam(value = "captcha_code") String captchaCode,
                                                                   HttpSession httpSession) {
        return ResponseEntity.ok(captchaService.validateCaptcha(captchaId, captchaCode,httpSession));
    }
}
