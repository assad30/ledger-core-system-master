package com.ledger.system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(

        @NotNull Long userAccountId,
        @NotNull Long merchantAccountId,
        @NotNull @DecimalMin(value = "0.01", inclusive = false) BigDecimal amount,
        @NotNull @DecimalMin(value = "0.00") BigDecimal fee,
        @NotBlank String idempotencyKey

) { }
