package br.com.compass.uol.service;

import br.com.compass.uol.dto.TransferRequest;
import br.com.compass.uol.dto.TransferResponse;
import br.com.compass.uol.model.Account;
import br.com.compass.uol.model.Transaction;
import br.com.compass.uol.repository.AccountRepository;
import br.com.compass.uol.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Should be success")
    void shouldTransferSuccessfully() {

       var receiver =  Account.builder().id(1L)
                .name("João")
                .balance(new BigDecimal("200.00"))
                .build();

        var sender = Account.builder().id(2L)
                .name("Maria")
                .balance(new BigDecimal("500.00"))
                .build();

        var request = new TransferRequest(
                1L,
                2L,
                new BigDecimal("100.00")
        );

        when(accountRepository.findByIdWithLock(1L))
                .thenReturn(Optional.of(sender));

        when(accountRepository.findByIdWithLock(2L))
                .thenReturn(Optional.of(receiver));

        var response = transactionService.transfer(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(new BigDecimal("400.00"), sender.getBalance());
        Assertions.assertEquals(new BigDecimal("100.00"), receiver.getBalance());


        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(notificationService, times(1)).sendNotification(any(TransferResponse.class));
    }

    @Test
    @DisplayName("Should be handle Exception insufficient balance ")
    void shouldThrowExceptionWhenInsufficientBalance() {

        var sender =  Account.builder()
                .id(1L)
                .name("Pedro")
                .balance(new BigDecimal("100.00"))
                .build();

        var receiver =  Account.builder()
                .id(2L)
                .name("João")
                .balance(new BigDecimal("500.00"))
                .build();

        var request = new TransferRequest(1L, 2L, new BigDecimal("500.00"));

        when(accountRepository.findByIdWithLock(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findByIdWithLock(2L)).thenReturn(Optional.of(receiver));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                transactionService.transfer(request));

        Assertions.assertEquals("Insufficient balance for transfer", exception.getMessage());

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));

    }


}