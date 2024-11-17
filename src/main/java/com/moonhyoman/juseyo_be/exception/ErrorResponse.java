package com.moonhyoman.juseyo_be.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status;// HTTP 상태 코드
    private final String code;// 비즈니스 오류 코드
    private final String message;//사용자 메시지
}

