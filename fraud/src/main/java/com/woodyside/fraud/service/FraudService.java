package com.woodyside.fraud.service;

import com.woodyside.fraud.exception.ClientIsFraudException;
import com.woodyside.fraud.model.FraudCheckHistory;
import com.woodyside.fraud.payload.response.IsFraudulentClientResponse;
import com.woodyside.fraud.repository.FraudRepository;
import com.woodyside.services.captcha.CaptchaService;
import com.woodyside.services.captcha.payload.response.GetCaptchaResponseBody;
import com.woodyside.services.client.ClientService;
import com.woodyside.services.client.payload.request.ClientUpdateFraudulentStatusRequest;
import com.woodyside.services.client.payload.response.ClientUpdateFraudulentStatusResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.woodyside.fraud.util.DateResponseFormatter.getTimestamp;

@Service
@AllArgsConstructor
public class FraudService {

    private final FraudRepository fraudRepository;

    private final CaptchaService captchaService;

    private final ClientService clientService;

    public IsFraudulentClientResponse isFraudulentClient(String captchaId) {

        ResponseEntity<GetCaptchaResponseBody> found = captchaService.findByCaptchaId(captchaId);

        boolean validation_flag;

        if(Objects.requireNonNull(found.getBody()).getValidatedResult()) {
            validation_flag = false;
        } else {
            validation_flag = true;
        }

        ResponseEntity<ClientUpdateFraudulentStatusResponse> isClientFraudResponse =
                clientService.updateFraudulentStatus(ClientUpdateFraudulentStatusRequest
                .builder()
                .isFraudster(validation_flag)
                .email(found.getBody().getEmail())
                .build());

        if(Objects.requireNonNull(isClientFraudResponse.getBody()).getIsFraudster()) {
            throw new ClientIsFraudException();
        }

        FraudCheckHistory fraudCheckHistory = FraudCheckHistory
                .builder()
                .clientUsername(found.getBody().getEmail())
                .isFraudster(validation_flag)
                .build();

        fraudRepository.insert(fraudCheckHistory);

        return IsFraudulentClientResponse.builder()
                .info("Client has processed authorization check with a result:" + Objects.requireNonNull(found.getBody().getValidatedResult()))
                .timestamp(getTimestamp())
                .build();
    }

}
