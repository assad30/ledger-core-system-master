package com.ledger.system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RefundRequest(

        @NotNull Long originalTransactionId,
        @NotNull @DecimalMin(value = "0.01", inclusive = false) BigDecimal amount,
        @NotBlank String idempotencyKey) { }
