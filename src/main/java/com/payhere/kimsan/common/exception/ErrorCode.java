package com.payhere.kimsan.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다."),
    DUPLICATED_ID(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "중복되는 아이디 입니다."),
    NOT_ALLOW_USER(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "허용되지 않은 사용자 입니다."),
    INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "ACCOUNT-004", "아이디 혹은 비밀번호가 잘못되었습니다"),
    PRODUCT_NOT_NULL(HttpStatus.BAD_REQUEST, "PRODUCT-001", "상품 등록시 null을 등록할 수 없습니다.");


    private final HttpStatus httpStatus;  // HttpStatus
    private final String code;        // ACCOUNT-001
    private final String message;      // 설명
}