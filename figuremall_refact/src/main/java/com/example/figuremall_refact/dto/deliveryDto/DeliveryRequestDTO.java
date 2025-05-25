package com.example.figuremall_refact.dto.deliveryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class DeliveryRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDeliveryDto {

        @NotBlank
        String address;
        String detail;
        @NotNull
        String postalCode;
        @NotBlank
        String recipientName;
        @NotBlank
        String recipientPhone;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateDeliveryDto {

        @NotNull
        Long deliveryId;
        LocalDateTime shippedAt;
        LocalDateTime deliveredAt;
        String address;
        String detail;
        String postalCode;
        String recipientName;
        String recipientPhone;
        String courierCompany;
        String trackingNumber;

    }
}
