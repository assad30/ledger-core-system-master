package com.ledger.system;

import com.ledger.system.enums.ErrorCode;
import com.ledger.system.exception.BusinessException;
import com.ledger.system.validation.LedgerValidationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LedgerValidationServiceTest {

    private final LedgerValidationService validationService = new LedgerValidationService();

    @Test
    void validateAmount_acceptsPositiveValues() {
        assertDoesNotThrow(() -> validationService.validateAmount(new BigDecimal("10.00")));
    }

    @Test
    void validateAmount_rejectsZeroOrNegativeValues() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateAmount(BigDecimal.TEN));
        assertEquals(ErrorCode.INVALID_AMOUNT, ex.getErrorCode());
    }

    @Test
    void validateAmount_rejectsNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateAmount(null));
        assertEquals(ErrorCode.INVALID_AMOUNT, ex.getErrorCode());
    }

    @Test
    void validateFee_rejectsFeeEqualToOrHigherThanAmount() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateFee(new BigDecimal("100.00"), new BigDecimal("100.00")));
        assertEquals(ErrorCode.INVALID_FEE, ex.getErrorCode());
    }

    @Test
    void validateFee_rejectsNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateFee(new BigDecimal("100.00"), null));
        assertEquals(ErrorCode.INVALID_FEE, ex.getErrorCode());
    }

    @Test
    void validateFee_rejectsNegative() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateFee(new BigDecimal("100.00"), new BigDecimal("-1.00")));
        assertEquals(ErrorCode.INVALID_FEE, ex.getErrorCode());
    }

    @Test
    void validateAccounts_rejectsSameUserAndMerchant() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateAccounts(1L, 1L));
        assertEquals(ErrorCode.ACCOUNT_INVALID, ex.getErrorCode());
    }

    @Test
    void validateAccounts_rejectsNullUser() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateAccounts(null, 2L));
        assertEquals(ErrorCode.ACCOUNT_INVALID, ex.getErrorCode());
    }

    @Test
    void validateAccounts_rejectsNullMerchant() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> validationService.validateAccounts(1L, null));
        assertEquals(ErrorCode.ACCOUNT_INVALID, ex.getErrorCode());
    }

    @Test
    void validatePayment_acceptsValidInputs() {
        assertDoesNotThrow(() -> validationService.validatePayment(new BigDecimal("100.00"), new BigDecimal("10.00"), 1L, 2L));
    }
}
