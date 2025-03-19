package com.example.figuremall_refact.dto.cartDto;

import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCartItemDto {

        @NotNull
        Long productId;
        @NotNull
        Integer quantity;
        @NotNull
        Long price;
        @NotNull
        Long productOptionValueId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditQuantityDto {

        @NotNull
        Long cartItemId;
        @NotNull
        Integer quantity;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteCartItemDto {

        @NotNull
        Long cartItemId;

    }

}
