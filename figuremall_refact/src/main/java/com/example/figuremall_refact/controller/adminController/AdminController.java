package com.example.figuremall_refact.controller.adminController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.productDto.ProductListResponse;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.dto.productDto.ProductResponseDTO;
import com.example.figuremall_refact.service.productService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    @GetMapping("/products")
    public ApiResponse<ProductListResponse> getProducts(@RequestParam(name = "search", defaultValue = "") String search,
                                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        return ApiResponse.onSuccess(productService.getProducts(search, page, size));
    }

    @PostMapping("/checkProduct")
    public ApiResponse<Boolean> checkProduct(@RequestBody ProductRequestDTO.CheckName request) {
        return ApiResponse.onSuccess(productService.checkName(request));
    }

}
