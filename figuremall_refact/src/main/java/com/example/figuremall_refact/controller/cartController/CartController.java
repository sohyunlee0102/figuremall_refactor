package com.example.figuremall_refact.controller.cartController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.cartDto.CartRequestDTO;
import com.example.figuremall_refact.dto.cartDto.CartResponseDTO;
import com.example.figuremall_refact.service.cartService.CartItemService;
import com.example.figuremall_refact.service.cartService.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping
    public ApiResponse<CartResponseDTO.AddCartItemResponseDto> addCartItem(@Valid @RequestBody CartRequestDTO.AddCartItemDto request,
                                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(cartItemService.addCartItem(request, userDetails.getUsername()));
    }

    @PutMapping
    public ApiResponse<CartResponseDTO.AddCartItemResponseDto> editQuantity(@Valid @RequestBody CartRequestDTO.EditQuantityDto request) {
        return ApiResponse.onSuccess(cartItemService.editQuantity(request));
    }

    @DeleteMapping
    public ApiResponse<String> deleteCartItem(@Valid @RequestBody CartRequestDTO.DeleteCartItemDto request) {
        cartItemService.deleteCartItem(request);
        return ApiResponse.onSuccess("장바구니 상품이 삭제되었습니다.");
    }

}
