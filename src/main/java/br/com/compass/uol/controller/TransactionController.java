package br.com.compass.uol.controller;

import br.com.compass.uol.dto.TransferRequest;
import br.com.compass.uol.dto.TransferResponse;
import br.com.compass.uol.model.Transaction;
import br.com.compass.uol.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransferResponse> transferToAccount(@RequestBody @Valid TransferRequest request) {
        TransferResponse response = transactionService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAllTransactions() {
        List<Transaction> transactions = transactionService.listAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
