package com.example.figuremall_refact.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddPostResponseDto {
        Long postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResponseDto {
        Long likeId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostList {
        Long id;
        String title;
        String content;
        Integer views;
        Integer likes;
        String writer;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResponse {
        Long id;
        String title;
        String content;
        Integer views;
        Integer likes;
        String writer;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        List<Files> files;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Files {
        private String fileName;
        private String fileUrl;
        private String fileType;
        private Long fileSize;
    }

}
