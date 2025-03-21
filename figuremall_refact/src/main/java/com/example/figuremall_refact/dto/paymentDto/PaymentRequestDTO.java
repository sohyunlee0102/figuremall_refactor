package com.example.figuremall_refact.dto.paymentDto;

import com.example.figuremall_refact.domain.enums.PaymentMethod;
import com.example.figuremall_refact.domain.enums.PaymentStatus;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class PaymentRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePaymentDto {

        @NotNull
        Long orderId;
        @NotNull
        BigDecimal amount;
        @NotNull
        PaymentMethod paymentMethod;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePaymentDto {

        @NotNull
        Long paymentId;
        PaymentStatus status;
        String transactionId;

    }

}
