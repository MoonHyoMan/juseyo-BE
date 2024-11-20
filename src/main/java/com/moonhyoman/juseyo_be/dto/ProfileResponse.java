package com.moonhyoman.juseyo_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponse {
    private String name;           // 사용자 이름
    private int totalPoint;        // 누적 적립 금액 (원)
    private int successCount;      // 성공한 미션 횟수
    private String level;          // 사용자 레벨
}
