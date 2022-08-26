package com.woodyside.client.service;


import com.woodyside.amqp.RabbitMQMessageProducer;
import com.woodyside.client.exception.EmailInUseException;
import com.woodyside.client.exception.NoClientFoundException;
import com.woodyside.client.model.Client;
import com.woodyside.client.model.ClientAddress;
import com.woodyside.client.model.ClientData;
import com.woodyside.client.payload.request.ClientRegistrationRequest;
import com.woodyside.client.payload.response.*;
import com.woodyside.client.payload.request.ClientUpdateFraudulentStatusRequest;
import com.woodyside.client.payload.request.ClientUpdateBalanceRequest;
import com.woodyside.client.repository.ClientRepository;
import com.woodyside.client.util.DateResponseFormatter;
import com.woodyside.services.notification.payload.request.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.woodyside.client.util.DateResponseFormatter.getTimestamp;

@Service
@AllArgsConstructor
@Slf4j
public class ClientCacheService {

    private final ClientRepository clientRepository;

    private final RabbitMQMessageProducer messageProducer;

    public void registerClient(ClientRegistrationRequest clientRegistrationRequest) {
        ClientData clientData = ClientData.builder()
                .firstName(clientRegistrationRequest.getFirstName())
                .lastName(clientRegistrationRequest.getLastName())
                .email(clientRegistrationRequest.getEmail())
                .phone(clientRegistrationRequest.getPhone())
                .build();
        ClientAddress clientAddress = ClientAddress.builder()
                .country(clientRegistrationRequest.getCountry())
                .city(clientRegistrationRequest.getCity())
                .region(clientRegistrationRequest.getRegion())
                .street(clientRegistrationRequest.getStreet())
                .zipCode(clientRegistrationRequest.getZipCode())
                .build();
        Client client = Client.builder()
                .clientData(clientData)
                .clientAddress(clientAddress)
                .currentSum(BigDecimal.valueOf(1_0000 + Math.random() * 100_00_00))
                .isFraudster(true)
                .build();

        log.info("========>    Registering client...\n\n");

        clientRepository.save(client);
    }

    public EmailInUseResponse ifClientEmailExists(String email) {
        Boolean emailInUse = clientRepository.existsByClientData_Email(email);
        if(emailInUse) {
            throw new EmailInUseException();
        }
        return EmailInUseResponse.builder().emailInUse(false).success(true).timestamp(getTimestamp())
                .build();
    }

    public ClientUpdateBalanceResponse updateBalanceResponse(ClientUpdateBalanceRequest updateStatusRequest) {
        Client clientFoundByEmail = clientRepository.findByClientData_Email(updateStatusRequest.getEmail())
                .orElseThrow(NoClientFoundException::new);

        clientFoundByEmail.setCurrentSum(updateStatusRequest.getCurrentSum());

        clientRepository.save(clientFoundByEmail);

        return ClientUpdateBalanceResponse.builder()
                .info("Client's balance was updated")
                .date(DateResponseFormatter.getTimestamp())
                .build();
    }

    public ClientUpdateFraudulentStatusResponse updateFraudsterStatus(ClientUpdateFraudulentStatusRequest request) {
        Client found = clientRepository.findByClientData_Email(request.getEmail())
                .orElseThrow(NoClientFoundException::new);
        found.setIsFraudster(request.getIsFraudster());

        ClientUpdateFraudulentStatusResponse response = ClientUpdateFraudulentStatusResponse.builder()
                .isFraudster(request.getIsFraudster())
                .timestamp(getTimestamp())
                .build();

        if(!response.getIsFraudster()) {
            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .toCustomerEmail(found.getClientData().getEmail())
                    .toCustomerId(found.getId())
                    .message("Congrats! You are not a fraud!")
                    .build();
            messageProducer.publish(
                    notificationRequest,
                     "internal.exchange",
                    "internal.notification.routing-key"
            );
        }
        clientRepository.save(found);
        return response;
    }

    public ClientFoundByEmailResponse findByClientEmail(String email) {
        Optional<Client> clientFoundByEmail = Optional.ofNullable(clientRepository.findByClientData_Email(email)
                .orElseThrow(NoClientFoundException::new));
        return ClientFoundByEmailResponse.builder()
                .id(clientFoundByEmail.get().getId())
                .firstName(clientFoundByEmail.get().getClientData().getFirstName())
                .lastName(clientFoundByEmail.get().getClientData().getLastName())
                .email(clientFoundByEmail.get().getClientData().getEmail())
                .currentBalance(clientFoundByEmail.get().getCurrentSum())
                .isFraudster(clientFoundByEmail.get().getIsFraudster())
                .timestamp(getTimestamp())
                .success(true)
                .build();
    }
}
