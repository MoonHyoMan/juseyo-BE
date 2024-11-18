package com.moonhyoman.juseyo_be.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Outcome 관련 오류
    INVALID_OUTCOME(200, "001", "User does not have outcome"),

    //교육 컨텐츠 관련 오류
    CONTENTS_NOT_FOUND(404, "C06", "Contents not found"),

    //퀴즈 관련 오류
    QUIZ_ANSWER_IS_NOT_VALID(400, "Q01", "answer is not valid"),
    QUIZ_ANSWER_IS_VALID(200, "Q03", "answer is valid"),
    QUIZ_NOT_FOUND(404, "Q02", "Quiz not found by Contents Id"),


    // Member 관련 오류
    INVALID_UNAUTHOR(401, "M01", "Unauthorized"),
    USER_ALREADY_EXISTS(400, "M02", "User already exists"),
    REGISTRATION_FAILED(400, "M03", "Registration failed"),
    EMAIL_NOT_FOUND(400, "M04", "Email not found"),
    INVALID_VERIFICATION_CODE(400, "M05", "Invalid verification code"),
    MEMBER_NOT_FOUND(404, "M06", "Member not found by ID"),
    TOKEN_IS_NOT_VALID(401, "M07", "Token is not valid"),
    PERMISSION_FORBIDDEN(403, "M08", "Permission forbidden"),
    NAVER_LOGIN_FAILED(500, "M09", "Naver login failed"),

    // 파일 관련 오류
    INVALID_FILE(400, "S01", "Invalid file"),
    NO_SUCH_ALGO(500, "S02", "Invalid algorithm");

    private final int status;        // HTTP 상태 코드
    private final String code;       // 비즈니스 오류 코드
    private final String message;    // 사용자 메시지
}
