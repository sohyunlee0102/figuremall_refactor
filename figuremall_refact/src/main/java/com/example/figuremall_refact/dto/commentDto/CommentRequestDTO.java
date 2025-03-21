package com.example.figuremall_refact.dto.commentDto;

import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CommentRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCommentDto {

        @NotBlank
        String content;
        @NotNull
        Long postId;
        Long parentId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditCommentDto {

        @NotNull
        Long commentId;
        @NotBlank
        String content;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteCommentDto {

        @NotNull
        Long commentId;

    }

}
