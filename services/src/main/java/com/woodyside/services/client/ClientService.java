package com.woodyside.services.client;

import com.woodyside.services.client.payload.request.ClientUpdateBalanceRequest;
import com.woodyside.services.client.payload.request.ClientUpdateFraudulentStatusRequest;
import com.woodyside.services.client.payload.response.ClientFoundByEmailResponse;
import com.woodyside.services.client.payload.response.ClientUpdateBalanceResponse;
import com.woodyside.services.client.payload.response.ClientUpdateFraudulentStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        value = "client",
        path =  "api/v1/client"
)
public interface ClientService {

    @GetMapping(value = "/{email}")
    ResponseEntity<ClientFoundByEmailResponse> findByEmail(@PathVariable(value = "email") String email);

    @PutMapping(path = "/updateFraudulentStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientUpdateFraudulentStatusResponse> updateFraudulentStatus(@RequestBody ClientUpdateFraudulentStatusRequest request);

    @PutMapping(path = "/updateBalance", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientUpdateBalanceResponse> updateClientBalance(@RequestBody ClientUpdateBalanceRequest request);
}
