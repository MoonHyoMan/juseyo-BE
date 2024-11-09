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
public class User {
    @Id
    private String id;

    private String password;
    private String name;
    private String accountNum;
    private String type;
    private int point;

    private String parentId;

    private String social;

    public void changePassword(String password){
        this.password = password;
    }

    public void linkSocial(String social){
        this.social = social;
    }
}
