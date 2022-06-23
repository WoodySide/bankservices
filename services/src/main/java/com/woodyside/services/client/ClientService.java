package com.woodyside.services.client;

import com.woodyside.services.client.payload.response.ClientFoundByEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        value = "client",
        path =  "api/v1/client"
)
public interface ClientService {

    @GetMapping(value = "/{email}")
    ResponseEntity<ClientFoundByEmailResponse> findByEmail(@PathVariable(value = "email") String email);
}
