package com.example.figuremall_refact.service.wishService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.WishlistHandler;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.domain.wishlist.Wishlist;
import com.example.figuremall_refact.dto.wishlistDto.WishlistRequestDTO;
import com.example.figuremall_refact.dto.wishlistDto.WishlistResponseDTO;
import com.example.figuremall_refact.repository.wishlistRepository.WishlistRepository;
import com.example.figuremall_refact.service.productService.ProductService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserService userService;
    private final ProductService productService;

    public Wishlist findById(Long id) {
        return wishlistRepository.findById(id).orElseThrow(() -> new WishlistHandler(ErrorStatus.WISHLIST_NOT_FOUND));
    }

    @Transactional
    public WishlistResponseDTO.AddWishlistResponseDto addWishlist(WishlistRequestDTO.AddWishlist request, String email) {
        User user = userService.findByEmail(email);
        Product product = productService.findProductById(request.getProductId());
        product.setLikeCount(product.getLikeCount() + 1);

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();

        return new WishlistResponseDTO.AddWishlistResponseDto(wishlistRepository.save(wishlist).getId());
    }

    @Transactional
    public void deleteWishlist(Long id) {
        Wishlist wishlist = findById(id);

        Product product = productService.findProductById(wishlist.getProduct().getId());
        product.setLikeCount(product.getLikeCount() - 1);

        wishlistRepository.delete(wishlist);
    }

}
