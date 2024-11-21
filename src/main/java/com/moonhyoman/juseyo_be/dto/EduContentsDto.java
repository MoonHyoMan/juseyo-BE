package com.moonhyoman.juseyo_be.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class EduContentsDto {
    private String eduType;
    private String link;
    private String category;
    private String titles;
    private List<EduQuizDto> quizzes;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EduQuizDto {
        private String question;
        private String answer;
    }
}

