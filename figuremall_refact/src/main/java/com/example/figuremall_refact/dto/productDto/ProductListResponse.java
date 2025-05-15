package com.example.figuremall_refact.dto.productDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductListResponse {

    private List<ProductResponseDTO.ProductListDto> products;
    private int totalPages;

}
