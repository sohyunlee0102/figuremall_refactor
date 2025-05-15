package com.example.figuremall_refact.controller.categoryController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.categoryDto.CategoryRequestDTO;
import com.example.figuremall_refact.dto.categoryDto.CategoryResponseDTO;
import com.example.figuremall_refact.service.categoryService.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponseDTO.CreateCategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDTO.CreateCategoryDto request) {
        return ApiResponse.onSuccess(categoryService.createCategory(request));
    }

    @PutMapping
    public ApiResponse<CategoryResponseDTO.CreateCategoryResponseDto> updateCategory(@Valid @RequestBody CategoryRequestDTO.UpdateCategoryDto request) {
        return ApiResponse.onSuccess(categoryService.updateCategory(request));
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.onSuccess("상품 카테고리가 삭제되었습니다.");
    }

    @GetMapping
    public ApiResponse<List<CategoryResponseDTO.getCategories>> getCategories() {
        return ApiResponse.onSuccess(categoryService.getCategories());
    }

}
