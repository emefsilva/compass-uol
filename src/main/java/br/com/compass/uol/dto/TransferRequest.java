package br.com.compass.uol.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull(message = "Id is required")
        long senderId,

        @NotNull(message = "Destiny of account is required")
        long receiverId,

        @NotNull(message = "Transfer value is required")
        @Positive(message = "Transfer value need to be bigger zero")
        BigDecimal amount
) {


}
