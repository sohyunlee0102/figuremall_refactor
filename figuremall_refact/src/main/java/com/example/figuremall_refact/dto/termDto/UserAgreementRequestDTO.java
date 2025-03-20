package com.example.figuremall_refact.dto.termDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserAgreementRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAgreementDto {

        @NotNull
        Long termId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteAgreementDto {

        @NotNull
        Long userAgreementId;

    }

}
