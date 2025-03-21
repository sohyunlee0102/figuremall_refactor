package com.example.figuremall_refact.dto.commentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCommentResponseDto {
        Long commentId;
    }

}
