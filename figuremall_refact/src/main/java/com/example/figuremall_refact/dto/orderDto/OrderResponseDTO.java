package com.example.figuremall_refact.dto.orderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderResponseDto {
        Long orderId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponseDto {
        Long orderItemId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderInfo {
        Long totalPrice;
        List<OrderItemInfo> items;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemInfo {
        Long itemId;
        String itemName;
        Long price;
        Integer quantity;
        List<OrderItemOptionInfo> options;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemOptionInfo {
        Long optionId;
        String optionName;
        Long extraPrice;
    }

}
