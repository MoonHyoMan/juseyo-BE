package com.moonhyoman.juseyo_be.controller;


import com.moonhyoman.juseyo_be.domain.EduQuiz;
import com.moonhyoman.juseyo_be.dto.AnswerSubmitDTO;
import com.moonhyoman.juseyo_be.exception.CustomException;
import com.moonhyoman.juseyo_be.exception.ErrorCode;
import com.moonhyoman.juseyo_be.exception.ErrorResponse;
import com.moonhyoman.juseyo_be.service.EduQuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - GET /education/quiz/{id} : 해당 콘텐츠 관련 퀴즈 조회
 * - POST /education/quiz/{id}/submit : 퀴즈 답안 제출
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
@Tag(name = "Education Quiz", description = "교육 컨텐츠 별 퀴즈 API")
public class EduQuizController {
    private final EduQuizService eduQuizService;

    @GetMapping("/{id}")
    @Operation(summary = "교육 퀴즈 조회", description = "교육 컨텐츠 고유id에 따른 퀴즈 목록 조회")
    public ResponseEntity<List<EduQuiz>> getQuiz(@PathVariable int id) {
        List<EduQuiz> quizList = eduQuizService.findQuizByGroupId(id);
        return ResponseEntity.ok(quizList);
    }
    @PostMapping("/submit")
    @Operation(summary = "교육 퀴즈 답안 제출", description = "교육 컨텐츠 고유id에 따른 퀴즈 제출 - 정답 시 pass, 오답 시 error")
    public ResponseEntity<ErrorResponse> checkAnswer(@RequestBody AnswerSubmitDTO dto) {
        if (!eduQuizService.findAnswerByQuiz(dto)) {
            throw new CustomException(ErrorCode.QUIZ_ANSWER_IS_NOT_VALID);//400
        }
        return ResponseEntity.ok(new ErrorResponse(ErrorCode.QUIZ_ANSWER_IS_VALID.getStatus(),ErrorCode.QUIZ_ANSWER_IS_VALID.getCode(),
                ErrorCode.QUIZ_ANSWER_IS_VALID.getMessage()));
    }



}
