package com.example.figuremall_refact.dto.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCategoryDto {

        @NotBlank
        String name;
        Long parentId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCategoryDto {

        @NotNull
        Long categoryId;
        String name;
        Long parentId;
        boolean parentIdUpdated;

    }

}
