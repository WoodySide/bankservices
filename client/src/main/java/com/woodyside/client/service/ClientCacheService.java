package com.woodyside.client.service;


import com.woodyside.client.exception.ClientIsFraudException;
import com.woodyside.client.exception.NoClientsFoundException;
import com.woodyside.client.model.Client;
import com.woodyside.client.model.ClientAddress;
import com.woodyside.client.model.ClientData;
import com.woodyside.client.payload.request.ClientRegistrationRequest;
import com.woodyside.client.repository.ClientRepository;
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

@Service
@AllArgsConstructor
@Slf4j
public class ClientCacheService {

    private final ClientRepository clientRepository;

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
                .isFraudster(false)
                .build();

        if(client.getIsFraudster()) {
            throw new ClientIsFraudException();
        }

        log.info("========>     Check has processed successfully, registering client...\n\n");

        clientRepository.save(client);
    }

    @Cacheable(value = "client", key = "#firstName")
    public Client findClientByFirstName(String firstName) {
        return clientRepository.findClientByFirstName(firstName)
                 .orElseThrow(NoClientsFoundException::new);
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

    public Boolean ifClientEmailExists(String email) {
        return clientRepository.existsByClientData_Email(email);
    }
}
