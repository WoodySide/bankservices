package com.woodyside.client.controller;

import com.woodyside.client.payload.request.ClientRegistrationRequest;
import com.woodyside.client.payload.request.ClientUpdateBalanceRequest;
import com.woodyside.client.payload.response.ClientUpdateBalanceResponse;
import com.woodyside.client.payload.request.ClientUpdateFraudulentStatusRequest;
import com.woodyside.client.payload.response.*;
import com.woodyside.client.service.ClientCacheService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "api/v1/client")
@AllArgsConstructor
public class ClientController {

    private final ClientCacheService clientService;

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientRegistrationResponse> registerClient(@Validated @RequestBody ClientRegistrationRequest registrationRequest) {

        clientService.registerClient(registrationRequest);

        return ResponseEntity
                .ok(ClientRegistrationResponse.builder()
                        .info("Client registered successfully")
                        .responseDate(LocalDateTime.now())
                        .success(true)
                        .build());
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientFoundByEmailResponse> findByEmail(@PathVariable(value = "email") String email) {
        return ResponseEntity.ok(clientService.findByClientEmail(email));
    }

    @GetMapping(path = "checkEmailInUse")
    public ResponseEntity<EmailInUseResponse> ifEmailExists(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(clientService.ifClientEmailExists(email));
    }

    @PutMapping(path = "/updateFraudulentStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientUpdateFraudulentStatusResponse> updateFraudulentStatus(@RequestBody ClientUpdateFraudulentStatusRequest request)  {
        ClientUpdateFraudulentStatusResponse response = clientService.updateFraudsterStatus(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/updateBalance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientUpdateBalanceResponse> updateClientBalance(@RequestBody ClientUpdateBalanceRequest request) {
        return ResponseEntity.ok(clientService.updateBalanceResponse(request));
    }
}
