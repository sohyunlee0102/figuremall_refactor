package com.example.figuremall_refact.dto.wishlistDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WishlistResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddWishlistResponseDto {
        Long wishlistId;
    }

}
