package com.example.figuremall_refact.dto.deliveryDto;

import com.example.figuremall_refact.domain.enums.DeliveryStatus;
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
        Integer postalCode;
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
        DeliveryStatus status;
        LocalDateTime shippedAt;
        LocalDateTime deliveredAt;
        String address;
        String detail;
        Integer postalCode;
        String recipientName;
        String recipientPhone;
        String courierCompany;
        String trackingNumber;

    }
}
