package com.example.figuremall_refact.service.cartService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.CartHandler;
import com.example.figuremall_refact.domain.cart.Cart;
import com.example.figuremall_refact.domain.cart.CartItem;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.cartDto.CartRequestDTO;
import com.example.figuremall_refact.dto.cartDto.CartResponseDTO;
import com.example.figuremall_refact.repository.cartRepository.CartItemRepository;
import com.example.figuremall_refact.service.productService.ProductOptionValueService;
import com.example.figuremall_refact.service.productService.ProductService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final CartItemOptionService cartItemOptionService;

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new CartHandler(ErrorStatus.CART_ITEM_NOT_FOUND));
    }

    @Transactional
    public CartResponseDTO.AddCartItemResponseDto addCartItem(CartRequestDTO.AddCartItemDto request, String email) {
        Product product = productService.findProductById(request.getProductId());
        User user = userService.findByEmail(email);
        Cart cart = cartService.findByUser(user).orElseThrow(() -> new CartHandler(ErrorStatus.CART_NOT_FOUND));

        CartItem cartItem = CartItem.builder()
                .product(product)
                .cart(cart)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        cartItemRepository.save(cartItem);
        cartItemOptionService.save(cartItem, request.getProductOptionValueId());

        return new CartResponseDTO.AddCartItemResponseDto(cartItem.getId());
    }
    
    @Transactional
    public CartResponseDTO.AddCartItemResponseDto editQuantity(CartRequestDTO.EditQuantityDto request) {
        CartItem cartItem = findById(request.getCartItemId());
        cartItem.setQuantity(request.getQuantity());

        return new CartResponseDTO.AddCartItemResponseDto(cartItem.getId());
    }

    @Transactional
    public void deleteCartItem(CartRequestDTO.DeleteCartItemDto request) {
        cartItemRepository.deleteById(request.getCartItemId());
    }

}
