package com.example.figuremall_refact.dto.userDto;

import com.example.figuremall_refact.domain.enums.Gender;
import com.example.figuremall_refact.domain.user.UserAddress;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class UserRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDto {

        @NotBlank
        @Email
        String email;
        @NotBlank
        String password;
        @NotBlank
        String username;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDto {

        @NotBlank
        @Email
        String email;
        @NotBlank
        String password;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshTokenDTO {

        @NotNull
        String refreshToken;
        @NotBlank
        @Email
        String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddAddressDTO {

        @NotBlank
        String address;
        String detail;
        @NotNull
        Integer postalCode;
        @NotNull
        Boolean isDefault;

    }

    @Getter
    @Setter
    public static class UpdateDTO {

        String username;
        String phoneNum;
        LocalDate dateOfBirth;
        Gender gender;
        List<UserAddressDTO> addresses;

    }

    @Getter
    @Setter
    public static class UserAddressDTO {
        Long id;
        String address;
        String detail;
        Integer postalCode;
    }

    @Getter
    @Setter
    public static class CheckEmailDuplicationDTO {
        @NotBlank
        String email;
    }

    @Getter
    @Setter
    public static class CheckUsernameDuplicationDTO {
        @NotBlank
        String username;
    }

}
