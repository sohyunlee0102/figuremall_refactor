package com.example.figuremall_refact.service.categoryService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.CategoryHandler;
import com.example.figuremall_refact.domain.category.Category;
import com.example.figuremall_refact.dto.categoryDto.CategoryRequestDTO;
import com.example.figuremall_refact.dto.categoryDto.CategoryResponseDTO;
import com.example.figuremall_refact.repository.categoryRepository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
    }

    @Transactional
    public CategoryResponseDTO.CreateCategoryResponseDto createCategory(CategoryRequestDTO.CreateCategoryDto request) {
        Category parentCategory = null;

        if (request.getParentId() != null) {
            parentCategory = findById(request.getParentId());
        }

        Category category = Category.builder()
                .name(request.getName())
                .parent(parentCategory)
                .build();

        return new CategoryResponseDTO.CreateCategoryResponseDto(categoryRepository.save(category).getId());
    }

    @Transactional
    public CategoryResponseDTO.CreateCategoryResponseDto updateCategory(CategoryRequestDTO.UpdateCategoryDto request) {
        Category category = findById(request.getCategoryId());

        if (request.getName() != null) {
            category.setName(request.getName());
        }

        if (request.getParentId() != null) {
            category.setParent(findById(request.getParentId()));
        }

        return new CategoryResponseDTO.CreateCategoryResponseDto(category.getId());
    }

    @Transactional
    public void deleteCategory(CategoryRequestDTO.DeleteCategoryDto request) {
        categoryRepository.deleteById(request.getCategoryId());
    }

}
