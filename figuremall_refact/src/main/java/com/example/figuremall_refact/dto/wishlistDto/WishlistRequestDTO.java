package com.example.figuremall_refact.dto.wishlistDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WishlistRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddWishlist {
        @NotNull
        Long productId;
    }

}
