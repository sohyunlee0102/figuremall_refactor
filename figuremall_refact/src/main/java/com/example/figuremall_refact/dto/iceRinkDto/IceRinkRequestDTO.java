package com.example.figuremall_refact.dto.iceRinkDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class IceRinkRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddIceRinkDto {

        @NotBlank
        String name;
        @NotBlank
        String city;
        @NotBlank
        String district;
        @NotBlank
        String detailedAddress;
        @NotBlank
        String phone;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateIceRinkDto {

        @NotNull
        Long iceRinkId;
        String name;
        String city;
        String district;
        String detailedAddress;
        String phone;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteIceRinkDto {

        @NotNull
        Long iceRinkId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddIceRinkSelectionDto {

        @NotNull
        List<Long> iceRinkIds;

    }

}
