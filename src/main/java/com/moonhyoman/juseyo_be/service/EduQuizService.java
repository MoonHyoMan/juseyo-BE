package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.EduQuiz;
import com.moonhyoman.juseyo_be.dto.AnswerSubmitDTO;
import com.moonhyoman.juseyo_be.exception.CustomException;
import com.moonhyoman.juseyo_be.exception.ErrorCode;
import com.moonhyoman.juseyo_be.repository.EduQuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EduQuizService {
    private final EduQuizRepository eduQuizRepository;
    public List<EduQuiz> findQuizByGroupId(int groupId) {
        List<EduQuiz> quizList = eduQuizRepository.findByGroupId(groupId);
        if (quizList.isEmpty()) {
            throw new CustomException(ErrorCode.QUIZ_NOT_FOUND);
        }
        return quizList;
    }
    public EduQuiz findQuizById(int quizId) {
        return eduQuizRepository.findById(quizId).orElse(null);
    }
    public boolean findAnswerByQuiz(AnswerSubmitDTO dto) {
        EduQuiz quiz = findQuizById(dto.getId());
        if (quiz == null) {
            throw new CustomException(ErrorCode.QUIZ_NOT_FOUND);//404
        }
        System.out.println(quiz.getAnswer().equals(dto.getAnswer()));
        return quiz.getAnswer().equals(dto.getAnswer());
    }
}
