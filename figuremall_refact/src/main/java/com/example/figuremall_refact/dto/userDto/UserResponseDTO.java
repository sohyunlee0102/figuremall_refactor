package com.example.figuremall_refact.dto.userDto;

import com.example.figuremall_refact.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        String email;
        String username;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponseDto {
        String username;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddAddressResponseDto {
        Long addressId;
        String address;
        String detail;
        Integer postalCode;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResponseDto {
        Long userId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoResponseDto {
        String username;
        String email;
        String phoneNum;
        Gender gender;
        Integer point;
        LocalDate dateOfBirth;
        String picture;
    }

}
