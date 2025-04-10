package com.example.figuremall_refact.dto.orderDto;

import com.example.figuremall_refact.domain.enums.OrderStatus;
import com.example.figuremall_refact.dto.deliveryDto.DeliveryRequestDTO;
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
        @NotNull
        DeliveryRequestDTO.CreateDeliveryDto delivery;

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderItemStatusDto {

        @NotNull
        Long orderItemId;
        @NotNull
        OrderStatus status;

    }

}
