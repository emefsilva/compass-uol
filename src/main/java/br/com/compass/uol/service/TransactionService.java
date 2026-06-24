package br.com.compass.uol.service;

import br.com.compass.uol.dto.TransferRequest;
import br.com.compass.uol.dto.TransferResponse;
import br.com.compass.uol.model.Account;
import br.com.compass.uol.model.Transaction;
import br.com.compass.uol.repository.AccountRepository;
import br.com.compass.uol.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final NotificationService  notificationService;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        Account sender = accountRepository.findByIdWithLock(request.senderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        Account receiver = accountRepository.findByIdWithLock(request.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));


        if(sender.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance for transfer");
        }

        sender.setBalance(sender.getBalance().abs().subtract(request.amount()));
        receiver.setBalance(receiver.getBalance().abs().subtract(request.amount()));


        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(request.amount());
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        TransferResponse response = new TransferResponse(
                transaction.getId(),
                sender.getId(),
                receiver.getId(),
                transaction.getAmount(),
                transaction.getCreatedAt()
        );

        notificationService.sendNotification(response);

        return response;

    }

    public List<Transaction> listAllTransactions() {
        return transactionRepository.findAll();
    }
}
