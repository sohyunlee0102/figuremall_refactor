package com.example.figuremall_refact.service.cartService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.CartHandler;
import com.example.figuremall_refact.domain.cart.CartItem;
import com.example.figuremall_refact.domain.cart.CartItemOption;
import com.example.figuremall_refact.domain.product.ProductOptionValue;
import com.example.figuremall_refact.repository.cartRepository.CartItemOptionRepository;
import com.example.figuremall_refact.service.productService.ProductOptionValueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemOptionService {

    private final CartItemOptionRepository cartItemOptionRepository;
    private final ProductOptionValueService productOptionValueService;

    public CartItemOption findById(Long id) {
        return cartItemOptionRepository.findById(id).orElseThrow(() -> new CartHandler(ErrorStatus.CART_ITEM_OPTION_NOT_FOUND));
    }

    @Transactional
    public void save(CartItem cartItem, Long productOptionValueId) {
        ProductOptionValue productOptionValue = productOptionValueService.findById(productOptionValueId);

        CartItemOption cartItemOption = CartItemOption.builder()
                .cartItem(cartItem)
                .productOptionValue(productOptionValue)
                .build();

        cartItemOptionRepository.save(cartItemOption);
    }

}
