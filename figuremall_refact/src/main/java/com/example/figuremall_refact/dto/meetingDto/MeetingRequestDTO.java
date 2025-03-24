package com.example.figuremall_refact.dto.meetingDto;

import com.example.figuremall_refact.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MeetingRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMeetingDto {

        @NotBlank
        String title;
        String description;
        @NotNull
        LocalDateTime startTime;
        @NotNull
        LocalDateTime endTime;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditMeetingDto {

        @NotNull
        Long meetingId;
        String title;
        String description;
        LocalDateTime startTime;
        LocalDateTime endTime;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteMeetingDto {

        @NotNull
        Long meetingId;
        String title;
        String description;
        LocalDateTime startTime;
        LocalDateTime endTime;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateParticipantDto {

        @NotNull
        Long meetingId;
        List<AddAvailableTimeDto> availableTimeDTOs;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateParticipantDto {

        List<AddAvailableTimeDto> availableTimeDTOs;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddAvailableTimeDto {

        @NotNull
        LocalDateTime startTime;
        @NotNull
        LocalDateTime endTime;

    }

}
