package com.example.figuremall_refact.service.cartService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.CartHandler;
import com.example.figuremall_refact.domain.cart.Cart;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.cartDto.CartResponseDTO;
import com.example.figuremall_refact.repository.cartRepository.CartRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new CartHandler(ErrorStatus.CART_NOT_FOUND));
    }

    public Optional<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Transactional
    public Cart createCart(User user) {
        Optional<Cart> existingCart = findByUser(user);
        if (existingCart.isPresent()) {
            return findById(existingCart.get().getId());
        }

        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart);
    }

}
