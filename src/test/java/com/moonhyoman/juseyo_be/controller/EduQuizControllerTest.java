package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.EduContents;
import com.moonhyoman.juseyo_be.domain.EduQuiz;
import com.moonhyoman.juseyo_be.dto.AnswerSubmitDTO;
import com.moonhyoman.juseyo_be.exception.CustomException;
import com.moonhyoman.juseyo_be.exception.ErrorCode;
import com.moonhyoman.juseyo_be.repository.EduQuizRepository;
import com.moonhyoman.juseyo_be.service.EduQuizService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EduQuizController.class)
class EduQuizControllerTest {

    @TestConfiguration
    static class SecurityTestConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EduQuizService eduQuizService;
    @MockBean
    private EduQuizRepository eduQuizRepository;

    @Test
    @DisplayName("GET /api/quiz/{id} - 퀴즈 목록 조회 성공")
    void getQuiz_Success() throws Exception {
        EduContents contents = EduContents.builder()
                .id(1)
                .eduType("videos")
                .link("http://example.com/video1")
                .category("science")
                .titles("Physics Basics")
                .build();

        EduQuiz quiz1 = EduQuiz.builder()
                .id(1)
                .eduType("videos")
                .contents("What is gravity?")
                .answer("Force of attraction")
                .group(contents)
                .build();

        EduQuiz quiz2 = EduQuiz.builder()
                .id(2)
                .eduType("videos")
                .contents("What is the speed of light?")
                .answer("299,792 km/s")
                .group(contents)
                .build();

        List<EduQuiz> quizList = Arrays.asList(quiz1, quiz2);

        when(eduQuizService.findQuizByGroupId(1)).thenReturn(quizList);

        // When & Then
        mockMvc.perform(get("/api/quiz/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].contents").value("What is gravity?"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].contents").value("What is the speed of light?"));
    }

    @Test
    @DisplayName("POST /api/quiz/submit - 퀴즈 정답 제출 성공")
    void checkAnswer_Success() throws Exception {
        AnswerSubmitDTO dto = new AnswerSubmitDTO(1, "X");
        EduContents contents = EduContents.builder()
                .id(1)
                .eduType("videos")
                .link("http://example.com/video1")
                .category("science")
                .titles("Physics Basics")
                .build();

        EduQuiz quiz = EduQuiz.builder()
                .id(1)
                .eduType("videos")
                .contents("What is gravity?")
                .answer("X")  // Ensure answer matches
                .group(contents)
                .build();

        when(eduQuizService.findQuizById(eq(1))).thenReturn(quiz);
        when(eduQuizService.findAnswerByQuiz(any(AnswerSubmitDTO.class))).thenReturn(true);


        mockMvc.perform(post("/api/quiz/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"answer\":\"X\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.code").value("Q03"))
                .andExpect(jsonPath("$.message").value("answer is valid"));
    }


    @Test
    @DisplayName("POST /api/quiz/{id}/submit - 퀴즈 정답 제출 실패")
    void checkAnswer_Failure() throws Exception {
        // Given
        AnswerSubmitDTO dto = new AnswerSubmitDTO(1, "WrongAnswer");

        when(eduQuizService.findAnswerByQuiz(dto))
                .thenThrow(new CustomException(ErrorCode.QUIZ_ANSWER_IS_NOT_VALID));

        // When & Then
        mockMvc.perform(post("/api/quiz/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"answer\":\"WrongAnswer\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("Q01"))
                .andExpect(jsonPath("$.message").value("answer is not valid"));
    }
}