package com.moonhyoman.juseyo_be.domain;

import jakarta.persistence.Entity;
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
public class RejectRequestMission {
    @Id
    public Long id;

    public String childId;
    public String parentId;

    public String startDate;
    public String endDate;

    public String content;

    public String category;

    public int point;
}
