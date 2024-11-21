package com.moonhyoman.juseyo_be.exception;
import lombok.extern.slf4j.Slf4j;
import com.moonhyoman.juseyo_be.repository.EduContentsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    EduContentsRepository customerMapper;

    /**
     * CustomException 처리
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        // 로그로 예외 정보 출력
        log.error("CustomException 발생: 코드={}, 메시지={}", errorCode.getCode(), errorCode.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getStatus(),
                errorCode.getCode(),
                errorCode.getMessage()
        );

        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    /**
     * 기타 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        // 로그로 예외 정보 출력
        log.error("Exception 발생: 메시지={}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                500,
                "UNKNOWN",
                "An unexpected error occurred"
        );

        return ResponseEntity.status(500).body(errorResponse);
    }
}