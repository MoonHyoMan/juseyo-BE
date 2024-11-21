package com.moonhyoman.juseyo_be.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse {
    private String id;          // 사용자 ID
    private String name;        // 이름
    private String password;    // 비밀번호
    private String accountNum;  // 계좌번호
    private String type;        // 부모/자녀 구분 (parent/child)

    private String parentName;

    private List<String> childNameList;
}