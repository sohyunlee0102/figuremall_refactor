package com.example.figuremall_refact.apiPayload.code.status;

import com.example.figuremall_refact.apiPayload.code.BaseErrorCode;
import com.example.figuremall_refact.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 멤버 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "존재하지 않는 사용자입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER4002", "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER4003", "비밀번호가 일치하지 않습니다."),
    INACTIVE_USER(HttpStatus.UNAUTHORIZED, "USER4004", "비활성화 된 사용자입니다."),
    ADDRESS_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4005", "존재하지 않는 주소입니다."),

    //인증 관련 에러
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH4001", "이메일 또는 비밀번호가 일치하지 않습니다."),
    DYNAMIC_KEY_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUTH4001", "사용자에 대한 동적 키가 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4011", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH4012", "토큰이 만료되었습니다."),
    VERIFICATION_CODE_WRONG(HttpStatus.BAD_REQUEST, "AUTH4021", "인증번호가 일치하지 않습니다."),
    VERIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH4022", "인증번호가 만료되었습니다."),
    LOGOUT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4023", "로그아웃된 토큰입니다."),

    // 상품 관련 에러
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRODUCT4001", "존재하지 않는 상품 ID 입니다."),
    PRODUCT_OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRODUCT4002", "존재하지 않는 상품 옵션 ID 입니다."),
    PRODUCT_OPTION_VALUE_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRODUCT4003", "존재하지 않는 상품 옵션 값 ID 입니다."),
    PRODUCT_IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRODUCT4004", "존재하지 않는 상품 이미지 ID 입니다."),

    // 찜 관련 에러
    WISHLIST_NOT_FOUND(HttpStatus.BAD_REQUEST, "WISHLIST4001", "존재하지 않는 찜 ID 입니다."),

    // 습관 관련 에러
    HABIT_NOT_FOUND(HttpStatus.BAD_REQUEST, "HABIT4001", "존재하지 않는 습관 ID 입니다."),
    HABIT_HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "HABIT4002", "습관 기록이 존재하지 않습니다."),
    HABIT_EXECUTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "HABIT4003", "해당 습관 기록에 대한 시간이 존재하지 않습니다."),
    TIME_RANGE_CONFLICT(HttpStatus.BAD_REQUEST, "HABIT4004", "시간 범위가 중복됩니다."),
    INVALID_TIME_RANGE(HttpStatus.BAD_REQUEST, "HABIT4005", "시작 시간이 종료 시간부터 이후일 수 없습니다."),

    // 돌 관련 에러
    STONE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STONE4001", "존재하지 않는 돌 ID 입니다."),

    //일기 관련 에러

    DIARY_NOT_FOUND(HttpStatus.BAD_REQUEST,"DIARY4001","해당 날짜에 기록한 일기가 존재하지 않습니다."),
    DIARY_ISNT_MINE(HttpStatus.BAD_REQUEST,"DIARY4002","일기의 id와 자신의 id가 일치하지 않습니다."),

    NO_MORE_AI_COUNT(HttpStatus.BAD_REQUEST,"GPTCREATE4001","더이상 ai 자동 생성을 할 수 없습니다."),
    //emotionNote(하루 일들) 관련 에러
    NOTE_NOT_FOUND(HttpStatus.BAD_REQUEST,"NOTE4001","해당 날짜에 기록한 일이 존재하지 않습니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }

}
