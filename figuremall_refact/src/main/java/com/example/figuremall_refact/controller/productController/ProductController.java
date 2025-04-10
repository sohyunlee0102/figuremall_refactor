package com.example.figuremall_refact.controller.productController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.dto.productDto.ProductResponseDTO;
import com.example.figuremall_refact.service.productService.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponseDTO.AddProductResponseDto> addProduct(
            @Valid @RequestPart ProductRequestDTO.AddProductDto request,
            @RequestPart MultipartFile[] files,
            @RequestPart ProductRequestDTO.ImageInfoDto imageInfo) {
        return ApiResponse.onSuccess(productService.addProduct(request, files, imageInfo));
    }

    @PutMapping
    public ApiResponse<ProductResponseDTO.AddProductResponseDto> editProduct(
            @Valid @RequestPart ProductRequestDTO.EditProductDto request,
            @RequestPart(required = false) MultipartFile[] files) {
        return ApiResponse.onSuccess(productService.editProduct(request, files));
    }

    @DeleteMapping
    public ApiResponse<String> deleteProduct(@Valid @RequestBody ProductRequestDTO.DeleteProductDto request) {
        productService.deleteProduct(request);
        return ApiResponse.onSuccess("상품이 삭제되었습니다.");
    }

    @GetMapping("/home")
    public ApiResponse<Slice<ProductResponseDTO.HomeResponseDto>> homeProducts(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        String email = null;

        if (userDetails != null) {
            email = userDetails.getUsername();
        }

        return ApiResponse.onSuccess(productService.getHomeProducts(pageable, email));
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponseDTO.ProductDto> getProduct(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        String email = null;

        if (userDetails != null) {
            email = userDetails.getUsername();
        }

        return ApiResponse.onSuccess(productService.getProduct(productId, email));
    }

}
