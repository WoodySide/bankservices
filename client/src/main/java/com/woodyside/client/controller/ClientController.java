package com.woodyside.client.controller;

import com.woodyside.client.model.Client;
import com.woodyside.client.payload.request.ClientRegistrationRequest;
import com.woodyside.client.payload.response.ClientRegistrationResponse;
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

    @GetMapping(path = "checkEmailInUse")
    public ResponseEntity<ClientRegistrationResponse> ifEmailExists(@RequestParam(value = "email") String email) {

        Boolean emailExists = clientService.ifClientEmailExists(email);

        return ResponseEntity.ok(ClientRegistrationResponse.builder()
                .info("Email in use: " + emailExists)
                .responseDate(LocalDateTime.now())
                .success(true)
                .build());
    }

    @GetMapping(path = "/{firstName}")
    public ResponseEntity<Client> findByFirstName(@PathVariable(value = "firstName") String firstName) {
        return ResponseEntity.ok(clientService.findClientByFirstName(firstName));
    }
}
