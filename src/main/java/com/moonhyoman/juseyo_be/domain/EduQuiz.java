package com.moonhyoman.juseyo_be.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EduQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String eduType;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy 로딩으로 성능 최적화
    @JoinColumn(name = "group_id", nullable = false) // 외래 키 이름 지정
    private EduContents group; // 교육 콘텐츠 참조

    @Column(nullable = false) // 유효성 검증
    private String contents; // 퀴즈 내용

    @Column(nullable = false)
    private String answer; // 퀴즈 정답
}
