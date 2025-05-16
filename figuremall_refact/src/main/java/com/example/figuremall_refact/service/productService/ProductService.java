package com.example.figuremall_refact.service.productService;

import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.product.ProductOption;
import com.example.figuremall_refact.domain.product.ProductOptionValue;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.productDto.ProductListResponse;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.dto.productDto.ProductResponseDTO;
import com.example.figuremall_refact.repository.productRepository.ProductRepository;
import com.example.figuremall_refact.repository.wishlistRepository.WishlistRepository;
import com.example.figuremall_refact.service.categoryService.CategoryService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionService productOptionService;
    private final ProductImageService productImageService;
    private final UserService userService;
    private final WishlistRepository wishlistRepository;
    private final CategoryService categoryService;

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public ProductResponseDTO.AddProductResponseDto addProduct(
            ProductRequestDTO.AddProductDto productInfo, MultipartFile[] files,
            ProductRequestDTO.ImageInfoDto imageInfo) {
        Product product = Product.builder()
                .name(productInfo.getName())
                .price(productInfo.getPrice())
                .category(categoryService.findById(productInfo.getCategoryId()))
                .likeCount(0)
                .description(productInfo.getDescription())
                .build();

        Product savedProduct = productRepository.save(product);

        productOptionService.saveProductOptions(product, productInfo.getOptions());
        productImageService.saveProductImages(product, files, imageInfo);

        return new ProductResponseDTO.AddProductResponseDto(savedProduct.getId());
    }

    @Transactional
    public ProductResponseDTO.AddProductResponseDto editProduct(ProductRequestDTO.EditProductDto request, MultipartFile[] files) {
        Product product = findProductById(request.getProductId());

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getOptions() != null) {
            productOptionService.editProductOptions(request.getOptions());
        }

        productImageService.updateProductImages(product, request.getImages(), request.getDeleteImageIds(), files);

        return new ProductResponseDTO.AddProductResponseDto(product.getId());
    }

    @Transactional
    public void deleteProduct(ProductRequestDTO.DeleteProductDto request) {
        productRepository.deleteById(request.getProductId());
    }

    @Transactional
    public List<ProductResponseDTO.HomeResponseDto> getHomeProducts(String email) {
        List<Product> products = productRepository.findTop7ByOrderByLikeCountDesc();
        User user = (email != null) ? userService.findByEmail(email) : null;

        return products.stream().map(product -> {
                    String mainImageUrl = productImageService.getMainImage(product);
                    Long wishlistIdLocal = null;
                    if (wishlistRepository.existsByUserAndProduct(user, product)) {
                        wishlistIdLocal = wishlistRepository.findByUserAndProduct(user, product).getId();
                    }

                    return new ProductResponseDTO.HomeResponseDto(product.getId(), product.getPrice(), product.getName(), product.getLikeCount(),
                            wishlistIdLocal, mainImageUrl);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDTO.ProductDto getProduct(Long productId, String email) {
        Product product = findProductById(productId);
        User user = (email != null) ? userService.findByEmail(email) : null;
        boolean isWishlisted = (user != null) && wishlistRepository.existsByUserAndProduct(user, product);

        List <ProductOption> options = product.getOptions();
        List <ProductResponseDTO.ProductOptionDto> productOptionDtos = new ArrayList<>();

        for (ProductOption option : options) {
            List<ProductResponseDTO.ProductOptionValueDto> optionValueDtos = new ArrayList<>();

            for (ProductOptionValue optionValue : option.getValues()) {
                optionValueDtos.add(
                        new ProductResponseDTO.ProductOptionValueDto(optionValue.getId(), optionValue.getValueName(),
                                optionValue.getExtraPrice(), optionValue.getIsSoldOut())
                );
            }

            productOptionDtos.add(
                    new ProductResponseDTO.ProductOptionDto(option, optionValueDtos)
            );
        }

        return new ProductResponseDTO.ProductDto(
                product.getId(),
                product.getPrice(),
                product.getRating(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                isWishlisted,
                product.getOptions(),
                product.getImages()
        );
    }

    @Transactional
    public ProductListResponse getProducts(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> productPage;

        if (search == null || search.trim().isEmpty()) {
            productPage = productRepository.findAll(pageable);
        } else {
            productPage = productRepository.findByNameContainingIgnoreCase(search, pageable);
        }

        List<ProductResponseDTO.ProductListDto> dtos = productPage
                .stream()
                .map(product -> new ProductResponseDTO.ProductListDto(
                        product.getId(),
                        product.getName(),
                        product.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new ProductListResponse(dtos, productPage.getTotalPages());
    }

    @Transactional
    public boolean checkName(ProductRequestDTO.CheckName request) {
        return productRepository.existsByName(request.getName());
    }

}
