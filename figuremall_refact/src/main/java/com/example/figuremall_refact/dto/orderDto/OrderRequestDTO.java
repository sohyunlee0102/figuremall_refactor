package com.example.figuremall_refact.dto.orderDto;

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
        Integer totalPrice;
        @NotNull
        List<CreateOrderItemDto> items;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderItemDto {

        @NotNull
        Long productId;
        @NotNull
        Integer quantity;
        @NotNull
        Integer price;
        @NotNull
        List<CreateOrderItemOptionDto> options;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderItemOptionDto {

        @NotNull
        Long productOptionValueId;

    }

}
