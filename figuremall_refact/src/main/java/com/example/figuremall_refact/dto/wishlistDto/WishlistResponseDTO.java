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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWishlist {
        Long productId;
        Integer price;
        String name;
        Integer likeCount;
        Long wishlistId;
        String mainImageUrl;
    }

}
