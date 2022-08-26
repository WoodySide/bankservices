package com.woodyside.transaction.controller;

import com.woodyside.transaction.payload.request.CommitTransactionRequest;
import com.woodyside.transaction.payload.response.CommitTransactionResponse;
import com.woodyside.transaction.payload.response.ShowCurrentClientBalanceResponse;
import com.woodyside.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(path = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommitTransactionResponse> withdrawClientsMoney(@RequestBody CommitTransactionRequest commitTransactionRequest) {
        return ResponseEntity.ok(transactionService.withdraw(commitTransactionRequest));
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommitTransactionResponse> addMoneyToClientsAccount(@RequestBody CommitTransactionRequest commitTransactionRequest) {
        return ResponseEntity.ok(transactionService.addMoney(commitTransactionRequest));
    }

    @GetMapping(path = "/show", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShowCurrentClientBalanceResponse> showClientsBalance(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok(transactionService.showCurrentBalance(username));
    }
}
