package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.EduContents;
import com.moonhyoman.juseyo_be.domain.EduQuiz;
import com.moonhyoman.juseyo_be.exception.CustomException;
import com.moonhyoman.juseyo_be.exception.ErrorCode;
import com.moonhyoman.juseyo_be.service.EduContentsService;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EduContentsController.class)
class EduContentsControllerTest {
    @TestConfiguration
    static class SecurityTestConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().anyRequest().permitAll();  // 모든 요청 허용
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EduContentsService eduContentsService;

    @Test
    @DisplayName("GET /api/education/videos - 교육 영상 목록 조회 성공")
    void getAllVideos_Success() throws Exception {
        EduContents video1 = EduContents.builder()
                .id(1)
                .eduType("videos")
                .link("http://example.com/video1")
                .category("science")
                .titles("Physics Basics")
                .quiz(Arrays.asList(
                        EduQuiz.builder()
                                .id(1)
                                .eduType("videos")
                                .contents("What is gravity?")
                                .answer("Force of attraction")
                                .build(),
                        EduQuiz.builder()
                                .id(2)
                                .eduType("videos")
                                .contents("What is the speed of light?")
                                .answer("299,792 km/s")
                                .build()
                ))
                .build();

        EduContents video2 = EduContents.builder()
                .id(2)
                .eduType("videos")
                .link("http://example.com/video2")
                .category("technology")
                .titles("Introduction to AI")
                .quiz(Collections.emptyList()) // 퀴즈 없음
                .build();

        List<EduContents> videoList = Arrays.asList(video1, video2);

        when(eduContentsService.findContentsByEduType("videos")).thenReturn(videoList);

        // When & Then
        mockMvc.perform(get("/api/education/videos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].eduType").value("videos"))
                .andExpect(jsonPath("$[0].titles").value("Physics Basics"))
                .andExpect(jsonPath("$[0].quiz.length()").value(2))
                .andExpect(jsonPath("$[1].titles").value("Introduction to AI"))
                .andExpect(jsonPath("$[1].quiz.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/education/articles - 교육 뉴스 기사 목록 조회 성공")
    void getAllArticles_Success() throws Exception {
        // Given
        EduContents article1 = EduContents.builder()
                .id(1)
                .eduType("articles")
                .link("http://example.com/article1")
                .category("science")
                .titles("Latest Research in Physics")
                .quiz(Arrays.asList(
                        EduQuiz.builder()
                                .id(3)
                                .eduType("articles")
                                .contents("Who discovered gravity?")
                                .answer("Isaac Newton")
                                .build()
                ))
                .build();

        EduContents article2 = EduContents.builder()
                .id(2)
                .eduType("articles")
                .link("http://example.com/article2")
                .category("technology")
                .titles("AI Ethics and Challenges")
                .quiz(Collections.emptyList()) // 퀴즈 없음
                .build();

        List<EduContents> articleList = Arrays.asList(article1, article2);

        when(eduContentsService.findContentsByEduType("articles")).thenReturn(articleList);

        // When & Then
        mockMvc.perform(get("/api/education/articles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].eduType").value("articles"))
                .andExpect(jsonPath("$[0].titles").value("Latest Research in Physics"))
                .andExpect(jsonPath("$[0].quiz.length()").value(1))
                .andExpect(jsonPath("$[1].titles").value("AI Ethics and Challenges"))
                .andExpect(jsonPath("$[1].quiz.length()").value(0));
    }


    @Test
    @DisplayName("GET /api/education/{id} - 교육 컨텐츠 개별 조회 성공")
    void getEduContentsById_Success() throws Exception {
        // Given
        EduContents contents = EduContents.builder()
                .id(1)
                .eduType("videos")
                .link("http://example.com/video1")
                .category("science")
                .titles("Physics Basics")
                .build();

        when(eduContentsService.findConentsById(1)).thenReturn(contents);

        // When & Then
        mockMvc.perform(get("/api/education/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.eduType").value("videos"))
                .andExpect(jsonPath("$.titles").value("Physics Basics"));
    }

    @Test
    @DisplayName("GET /api/education/{id} - 교육 컨텐츠 개별 조회 실패 (컨텐츠 없음)")
    void getEduContentsById_Failure_NotFound() throws Exception {
        // Given
        int invalidId = 999;
        when(eduContentsService.findConentsById(invalidId))
                .thenThrow(new CustomException(ErrorCode.CONTENTS_NOT_FOUND));

        // When & Then
        mockMvc.perform(get("/api/education/" + invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value("C06"))
                .andExpect(jsonPath("$.message").value("Contents not found"));
    }


}
