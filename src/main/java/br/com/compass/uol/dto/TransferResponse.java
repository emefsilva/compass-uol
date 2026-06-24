package br.com.compass.uol.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(
        Long transactionId,
        Long senderId,
        Long receiverId,
        BigDecimal amount,
        LocalDateTime date

) {

}
