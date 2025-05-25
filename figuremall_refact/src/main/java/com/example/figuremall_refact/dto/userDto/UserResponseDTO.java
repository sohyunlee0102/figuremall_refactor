package com.example.figuremall_refact.dto.userDto;

import com.example.figuremall_refact.domain.enums.Gender;
import com.example.figuremall_refact.domain.enums.Role;
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
        Role role;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddAddressResponseDto {
        Long addressId;
        String address;
        String detail;
        String postalCode;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        Long id;
        String address;
        String detail;
        String postalCode;
        boolean isDefault;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderUserInfo {
        String username;
        String phoneNum;
        Long addressId;
        String address;
        String detail;
        String postalCode;
    }

}
