package com.example.figuremall_refact.dto.postDto;

import com.example.figuremall_refact.domain.enums.PostCategory;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddPostDto {

        @NotBlank
        String title;
        @NotBlank
        String content;
        @NotNull
        PostCategory category;
        List<AddPostFileDto> files;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddPostFileDto {

        @NotBlank
        String fileName;
        @NotBlank
        String fileType;
        @NotNull
        Long fileSize;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditPostDto {

        @NotNull
        Long postId;
        String title;
        String content;
        PostCategory category;
        List<Long> deleteFileIds;
        List<AddPostFileDto> files;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeletePostDto {

        @NotNull
        Long postId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddLikeDto {

        @NotNull
        Long postId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteLikeDto {

        @NotNull
        Long likeId;

    }

}
