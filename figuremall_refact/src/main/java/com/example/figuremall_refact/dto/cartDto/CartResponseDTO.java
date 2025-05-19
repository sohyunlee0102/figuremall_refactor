package com.example.figuremall_refact.dto.cartDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCartItemResponseDto {
        Long cartItemId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItem {
        Long itemId;
        Long productId;
        String productName;
        Integer quantity;
        Long price;
        String imageUrl;
        List<CartItemOptions> options;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemOptions {
        Long optionId;
        String optionName;

    }

}
