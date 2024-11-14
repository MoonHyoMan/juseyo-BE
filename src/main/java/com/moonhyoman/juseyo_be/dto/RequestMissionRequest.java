package com.moonhyoman.juseyo_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RequestMissionRequest {
    public Date startDate;
    public Date endDate;

    public String content;

    public String category;

    public int point;
}
