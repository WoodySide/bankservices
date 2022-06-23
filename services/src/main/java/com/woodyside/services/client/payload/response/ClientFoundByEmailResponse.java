package com.woodyside.services.client.payload.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientFoundByEmailResponse {

    private String firstName;

    private String lastName;

    private String email;

    private Boolean isFraudster;

    private Boolean success;

    private String timestamp;
}
