package com.example.figuremall_refact.service.productService;

import com.example.figuremall_refact.domain.product.ProductOption;
import com.example.figuremall_refact.domain.product.ProductOptionValue;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.repository.productRepository.ProductOptionValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionValueService {

    private final ProductOptionValueRepository productOptionValueRepository;

    public void saveValues(ProductOption productOption, List<ProductRequestDTO.ProductOptionValueDTO> valueDTOS) {
        for (ProductRequestDTO.ProductOptionValueDTO valueDTO : valueDTOS) {
            ProductOptionValue productOptionValue = ProductOptionValue.builder()
                    .productOption(productOption)
                    .isSoldOut(false)
                    .valueName(valueDTO.getValueName())
                    .extraPrice(valueDTO.getExtraPrice())
                    .stock(valueDTO.getStock())
                    .build();

            productOptionValueRepository.save(productOptionValue);
        }
    }

}
