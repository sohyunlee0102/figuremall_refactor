package com.example.figuremall_refact.dto.noticeDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class NoticeRequestDTO {

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class CreateNoticeDTO {

        @NotBlank
        String title;
        @NotBlank
        String content;

    }

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class UpdateNoticeDTO {

        @NotNull
        Long noticeId;
        String title;
        String content;

    }

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class DeleteNoticeDTO {

        @NotNull
        Long noticeId;

    }

}