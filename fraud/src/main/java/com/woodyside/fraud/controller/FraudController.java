package com.woodyside.fraud.controller;

import com.woodyside.fraud.payload.response.IsFraudulentClientResponse;
import com.woodyside.fraud.service.FraudService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/fraud")
public class FraudController {

    private final FraudService fraudService;

    @GetMapping(value = "/checkAuth/{captcha_id}")
    public ResponseEntity<IsFraudulentClientResponse> checkClient(@PathVariable(value = "captcha_id") String captchaId) {
        return ResponseEntity.ok(fraudService.isFraudulentClient(captchaId));
    }
}
