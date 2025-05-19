package com.example.figuremall_refact.service.cartService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.CartHandler;
import com.example.figuremall_refact.domain.cart.Cart;
import com.example.figuremall_refact.domain.cart.CartItem;
import com.example.figuremall_refact.domain.cart.CartItemOption;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.cartDto.CartRequestDTO;
import com.example.figuremall_refact.dto.cartDto.CartResponseDTO;
import com.example.figuremall_refact.repository.cartRepository.CartItemRepository;
import com.example.figuremall_refact.service.productService.ProductImageService;
import com.example.figuremall_refact.service.productService.ProductOptionValueService;
import com.example.figuremall_refact.service.productService.ProductService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final CartItemOptionService cartItemOptionService;
    private final ProductImageService productImageService;

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new CartHandler(ErrorStatus.CART_ITEM_NOT_FOUND));
    }

    @Transactional
    public CartResponseDTO.AddCartItemResponseDto addCartItem(CartRequestDTO.AddCartItemDto request, String email) {
        Product product = productService.findProductById(request.getProductId());
        User user = userService.findByEmail(email);
        Cart cart = cartService.createCart(user);
        List<CartItemOption> options = new ArrayList<>();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .cart(cart)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        cartItemRepository.save(cartItem);

        for (Long valueId : request.getValues()) {
            options.add(cartItemOptionService.save(cartItem, valueId));
        }

        cartItem.setCartItemOptions(options);

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
        cartItemRepository.deleteAllByIdIn(request.getItemIds());
    }

    @Transactional
    public List<CartResponseDTO.CartItem> getCartItems(String email) {
        User user = userService.findByEmail(email);
        Optional<Cart> cart = cartService.findByUser(user);

        List<CartItem> items = cart.get().getCartItems();
        List<CartResponseDTO.CartItem> cartItems = new ArrayList<>();

        for (CartItem cartItem : items) {
            List<CartItemOption> options = cartItem.getCartItemOptions();
            List<CartResponseDTO.CartItemOptions> cartItemOptions = new ArrayList<>();

            for (CartItemOption option : options) {
                cartItemOptions.add(new CartResponseDTO.CartItemOptions(option.getId(), option.getProductOptionValue().getValueName()));
                System.out.println(option.getProductOptionValue().getValueName());
            }

            String imageUrl = productImageService.getMainImage(cartItem.getProduct());

            cartItems.add(new CartResponseDTO.CartItem(cartItem.getId(), cartItem.getProduct().getId(), cartItem.getProduct().getName(),
                    cartItem.getQuantity(), cartItem.getPrice(), imageUrl, cartItemOptions));
        }

        return cartItems;
    }

}
