package com.woodyside.transaction.service;


import com.woodyside.amqp.RabbitMQMessageProducer;
import com.woodyside.client.exception.ClientIsFraudException;
import com.woodyside.client.util.DateResponseFormatter;
import com.woodyside.services.client.ClientService;
import com.woodyside.services.client.payload.request.ClientUpdateBalanceRequest;
import com.woodyside.services.client.payload.response.ClientFoundByEmailResponse;
import com.woodyside.services.notification.payload.request.NotificationRequest;
import com.woodyside.transaction.model.Transaction;
import com.woodyside.transaction.payload.request.CommitTransactionRequest;
import com.woodyside.transaction.payload.response.CommitTransactionResponse;
import com.woodyside.transaction.payload.response.ShowCurrentClientBalanceResponse;
import com.woodyside.transaction.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.woodyside.transaction.model.TransactionKind.REPLENISHMENT;
import static com.woodyside.transaction.model.TransactionKind.WITHDRAWING;
import static com.woodyside.transaction.model.TransactionStatus.CONFIRMED;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final ClientService clientService;

    private final RabbitMQMessageProducer messageProducer;

    public CommitTransactionResponse withdraw(CommitTransactionRequest request) {

        ResponseEntity<ClientFoundByEmailResponse> foundByEmail =
                clientService.findByEmail(request.getClientUsername());

        if(!foundByEmail.getBody().getIsFraudster()) {

            BigDecimal currentBalance = foundByEmail.getBody().getCurrentBalance();

            currentBalance = currentBalance.subtract(request.getMoneyToOperate());

            clientService.updateClientBalance(
                    ClientUpdateBalanceRequest.builder()
                    .currentSum(currentBalance)
                    .email(request.getClientUsername())
                    .build());

            transactionRepository.save(
                   Transaction.builder()
                   .author(request.getClientUsername())
                   .transactionStatus(CONFIRMED)
                   .transactionKind(WITHDRAWING)
                   .operationSum(request.getMoneyToOperate())
                   .build());

                NotificationRequest notificationRequest = NotificationRequest.builder()
                        .toCustomerEmail(foundByEmail.getBody().getEmail())
                        .toCustomerId(foundByEmail.getBody().getId())
                        .message("Money withdrawing registered!")
                        .build();
                messageProducer.publish(
                        notificationRequest,
                        "internal.exchange",
                        "internal.notification.routing-key"
                );
            }

       return CommitTransactionResponse.builder()
               .info("There was money withdrawing got detected by: " + request.getClientUsername())
               .date(DateResponseFormatter.getTimestamp())
               .build();
    }

    public CommitTransactionResponse addMoney(CommitTransactionRequest request) {

        ResponseEntity<ClientFoundByEmailResponse> foundByEmail =
                clientService.findByEmail(request.getClientUsername());

        if(!foundByEmail.getBody().getIsFraudster()) {

            BigDecimal currentBalance = foundByEmail.getBody().getCurrentBalance();

            currentBalance = currentBalance.add(request.getMoneyToOperate());

            clientService.updateClientBalance(
                    ClientUpdateBalanceRequest.builder()
                            .currentSum(currentBalance)
                            .email(request.getClientUsername())
                            .build());

            transactionRepository.save(
                    Transaction.builder()
                            .author(request.getClientUsername())
                            .transactionStatus(CONFIRMED)
                            .transactionKind(REPLENISHMENT)
                            .operationSum(request.getMoneyToOperate())
                            .build());

            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .toCustomerEmail(foundByEmail.getBody().getEmail())
                    .toCustomerId(foundByEmail.getBody().getId())
                    .message("Money replenishment registered!")
                    .build();
            messageProducer.publish(
                    notificationRequest,
                    "internal.exchange",
                    "internal.notification.routing-key"
            );
        }

        return CommitTransactionResponse.builder()
                .info("There was money replenishment got detected by: " + request.getClientUsername())
                .date(DateResponseFormatter.getTimestamp())
                .build();

        }

    public ShowCurrentClientBalanceResponse showCurrentBalance(String username) {

        ResponseEntity<ClientFoundByEmailResponse> foundByEmail =
                clientService.findByEmail(username);

        if(foundByEmail.getBody().getIsFraudster()) {
            throw new ClientIsFraudException();
        }

        return ShowCurrentClientBalanceResponse.builder()
                .currentBalance(foundByEmail.getBody().getCurrentBalance())
                .date(DateResponseFormatter.getTimestamp())
                .build();
    }
}
