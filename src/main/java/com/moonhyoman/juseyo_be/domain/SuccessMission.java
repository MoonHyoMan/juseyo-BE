package com.moonhyoman.juseyo_be.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String childId;       // 자녀 ID
    private String parentId;      // 부모 ID
    private String content;       // 미션 내용
    private String category;      // 미션 카테고리

    private String startDate;     // 미션 시작 날짜
    private String endDate;       // 미션 마감 날짜
    private String doneDate;      // 미션 완료 날짜

    private int point;            // 미션 성공 시 적립 금액 (원)
}
