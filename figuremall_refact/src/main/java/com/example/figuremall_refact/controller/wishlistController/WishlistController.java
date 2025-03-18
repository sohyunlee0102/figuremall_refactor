package com.example.figuremall_refact.controller.wishlistController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.wishlistDto.WishlistRequestDTO;
import com.example.figuremall_refact.dto.wishlistDto.WishlistResponseDTO;
import com.example.figuremall_refact.service.wishService.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ApiResponse<WishlistResponseDTO.AddWishlistResponseDto> addWishlist(@Valid @RequestBody WishlistRequestDTO.AddWishlist request,
                                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(wishlistService.addWishlist(request, userDetails.getUsername()));
    }

    @DeleteMapping
    public ApiResponse<String> deleteWishlist(@Valid @RequestBody WishlistRequestDTO.DeleteWishlist request) {
        wishlistService.deleteWishlist(request.getWishlistId());
        return ApiResponse.onSuccess("찜 해제가 완료되었습니다.");
    }

}
