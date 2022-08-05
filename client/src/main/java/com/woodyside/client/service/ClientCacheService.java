package com.woodyside.client.service;


import com.woodyside.amqp.producer.RabbitMQMessageProducer;
import com.woodyside.client.exception.EmailInUseException;
import com.woodyside.client.exception.NoClientFoundException;
import com.woodyside.client.model.Client;
import com.woodyside.client.model.ClientAddress;
import com.woodyside.client.model.ClientData;
import com.woodyside.client.payload.request.ClientRegistrationRequest;
import com.woodyside.client.payload.request.ClientUpdateFraudulentStatusRequest;
import com.woodyside.client.payload.response.ClientFoundByEmailResponse;
import com.woodyside.client.payload.response.ClientUpdateFraudulentStatusResponse;
import com.woodyside.client.payload.response.EmailInUseResponse;
import com.woodyside.client.repository.ClientRepository;
import com.woodyside.services.notification.payload.request.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

        log.info("========>     Check has processed successfully, registering client...\n\n");

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

    @Cacheable(value = "client", key = "#email")
    public ClientFoundByEmailResponse findByClientEmail(String email) {
        Optional<Client> clientFoundByEmail = Optional.ofNullable(clientRepository.findByClientData_Email(email)
                .orElseThrow(NoClientFoundException::new));

        return ClientFoundByEmailResponse.builder()
                .firstName(clientFoundByEmail.get().getClientData().getFirstName())
                .lastName(clientFoundByEmail.get().getClientData().getLastName())
                .email(clientFoundByEmail.get().getClientData().getEmail())
                .isFraudster(true)
                .timestamp(getTimestamp())
                .success(true)
                .build();
    }

    @CacheEvict(allEntries = true, value = "client")
    @Scheduled(fixedRate = 3600000, initialDelay = 3600000)
    public void deleteAndEvictCache() {
        LocalDate anotherSummerDay = LocalDate.now();
        LocalTime anotherTime = LocalTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime
                .of(anotherSummerDay, anotherTime, ZoneId.of("Europe/Moscow"));
        log.info("Clearing cache, time: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
        .format(zonedDateTime));
    }
}
