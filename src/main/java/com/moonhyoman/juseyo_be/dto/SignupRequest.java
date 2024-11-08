package com.moonhyoman.juseyo_be.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @Id
    private String id;

    private String password;
    private String name;
    private String accountNum;
    private String type;
    private Integer point;
    private String parentId;
}
