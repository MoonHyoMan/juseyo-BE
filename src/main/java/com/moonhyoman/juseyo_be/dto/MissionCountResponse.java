package com.moonhyoman.juseyo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MissionCountResponse {
    private int completedMissionCount; // 완료한 미션 횟수
}
