package com.example.figuremall_refact.dto.termDto;

import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class TermRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateTermDto {

        @NotBlank
        String title;
        @NotBlank
        String content;
        @NotNull
        Float version;
        @NotNull
        Boolean isOptional;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTermDto {

        @NotNull
        Long termId;
        String title;
        String content;
        Float version;
        Boolean isOptional;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteTermDto {

        @NotNull
        Long termId;

    }

}
