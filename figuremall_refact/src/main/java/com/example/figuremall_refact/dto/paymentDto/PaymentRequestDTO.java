package com.example.figuremall_refact.dto.paymentDto;

import com.example.figuremall_refact.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
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
        String transactionId;

    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentVerifyRequest {

        Long orderId;
        String imp_uid;
        String merchant_uid;
        String paymentMethod;
        Integer amount;

        ShippingInfo shippingInfo;

        @Data
        public static class ShippingInfo {
            String name;
            String phone;
            String address;
            String detail;
            String postalCode;
            String deliveryNotes;
        }

        /*
        @Data
        public static class OrderItem {
            Long productId;
            String itemName;
            Integer quantity;
            List<Option> options;

            @Data
            public static class Option {
                String optionName;
            }
        }

         */
    }
}
