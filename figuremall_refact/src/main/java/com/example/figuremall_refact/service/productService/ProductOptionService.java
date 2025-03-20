package com.example.figuremall_refact.service.productService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.ProductHandler;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.product.ProductOption;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.repository.productRepository.ProductOptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionValueService productOptionValueService;

    public ProductOption findById(Long id) {
        return productOptionRepository.findById(id).orElseThrow(() -> new ProductHandler(ErrorStatus.PRODUCT_OPTION_NOT_FOUND));
    }

    @Transactional
    public void saveProductOptions(Product product, List<ProductRequestDTO.ProductOptionDTO> optionDTOS) {
        for (ProductRequestDTO.ProductOptionDTO optionDTO : optionDTOS) {
            ProductOption productOption = ProductOption.builder()
                    .product(product)
                    .optionName(optionDTO.getOptionName())
                    .build();

            productOptionRepository.save(productOption);
            productOptionValueService.saveValues(productOption, optionDTO.getValues());
        }
    }

    @Transactional
    public void editProductOptions(List<ProductRequestDTO.EditProductOptionDTO> requestList) {
        for (ProductRequestDTO.EditProductOptionDTO request : requestList) {
            ProductOption productOption = findById(request.getOptionId());

            if (request.getOptionName() != null) {
                productOption.setOptionName(request.getOptionName());
            }

            if (request.getValues() != null) {
                productOptionValueService.editValues(request.getValues());
            }
        }
    }

}
