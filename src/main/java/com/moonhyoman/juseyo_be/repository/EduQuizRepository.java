package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.EduQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EduQuizRepository extends JpaRepository<EduQuiz, Integer> {
    // 특정 컨텐츠에 속한 모든 퀴즈를 조회
    List<EduQuiz> findByGroupId(Integer groupId);
    //퀴즈 번호로 퀴즈 조회
    EduQuiz findEduQuizById(Integer id);
}