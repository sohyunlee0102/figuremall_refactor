package com.example.figuremall_refact.dto.orderDto;

import com.example.figuremall_refact.domain.enums.OrderItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderDto {

        @NotNull
        Long totalPrice;
        @NotNull
        List<CreateOrderItemDto> items;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderItemDto {

        @NotNull
        Long cartItemId;
        @NotNull
        List<CreateOrderItemOptionDto> options;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderItemOptionDto {

        @NotNull
        Long optionId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderItemStatusDto {

        @NotNull
        Long orderItemId;
        @NotNull
        OrderItemStatus status;

    }

}
