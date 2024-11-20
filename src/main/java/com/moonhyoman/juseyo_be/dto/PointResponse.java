package com.moonhyoman.juseyo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointResponse {
    private int totalPoints; // 적립된 총 금액 (원)
}
