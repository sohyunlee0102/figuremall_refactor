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

    // 리뷰 관련 에러
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "HABIT4001", "존재하지 않는 리뷰 ID 입니다."),
    REVIEW_IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "HABIT4002", "존재하지 않는 리뷰 이미지 ID 입니다."),
    REVIEW_NOT_OWNED(HttpStatus.BAD_REQUEST, "HABIT4003", "리뷰 수정 권한이 없습니다."),

    // 문의 관련 에러
    INQUIRY_NOT_FOUND(HttpStatus.BAD_REQUEST, "INQUIRY4001", "존재하지 않는 문의 ID 입니다."),
    INQUIRY_RESPONSE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STONE4002", "존재하지 않는 문의 답변 ID 입니다."),

    // 상품 카테고리 관련 에러
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY4001", "존재하지 않는 카테고리 ID 입니다."),

    // 약관 관련 에러
    TERM_NOT_FOUND(HttpStatus.BAD_REQUEST, "TERM4001", "존재하지 않는 이용약관 ID 입니다."),
    USER_AGREEMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "AGREEMENT4001", "존재하지 않는 약관 동의 기록 ID 입니다."),

    // 장바구니 관련 에러
    CART_NOT_FOUND(HttpStatus.BAD_REQUEST,"CART4001","존재하지 않는 장바구니 ID 입니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST,"CART4003","존재하지 않는 장바구니 상품 ID 입니다."),
    CART_ITEM_OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST,"CART4004","존재하지 않는 장바구니 상품 옵션 ID 입니다."),

    // 주문 관련 에러
    ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST,"ORDER4001","존재하지 않는 주문 ID 입니다."),
    ORDER_ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST,"ORDER4002","존재하지 않는 주문 상품 ID 입니다."),
    ORDER_ITEM_OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST,"ORDER4003","존재하지 않는 주문 상품 옵션 ID 입니다."),

    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST,"POST4001","존재하지 않는 게시글 ID 입니다."),
    POST_FILE_NOT_FOUND(HttpStatus.BAD_REQUEST,"POST4002","존재하지 않는 게시글 첨부파일 ID 입니다."),
    LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST,"ORDER4003","존재하지 않는 좋아요 ID 입니다."),
    DATA_NUMBER_NOT_SAME(HttpStatus.BAD_REQUEST,"POST4004","파일 개수와 요청 데이터 개수가 일치하지 않습니다."),

    // 댓글 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,"COMMENT4001","존재하지 않는 댓글 ID 입니다."),

    // 신고 관련 에러
    REPORT_NOT_FOUND(HttpStatus.BAD_REQUEST,"REPORT4001","존재하지 않는 신고 ID 입니다."),

    // 공지 관련 에러
    NOTICE_NOT_FOUND(HttpStatus.BAD_REQUEST,"NOTICE4001","존재하지 않는 공지 ID 입니다."),

    // 결제 관련 에러
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,"PAYMENT4001","존재하지 않는 결제 ID 입니다."),
    TRANSACTION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"PAYMENT4002","이미 존재하는 거래 ID 입니다.");

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
