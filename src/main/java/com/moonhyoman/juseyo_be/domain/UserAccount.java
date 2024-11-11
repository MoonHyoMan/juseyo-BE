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
public class UserAccount {
    @Id
    public String id;

    public String depositer;
    public String depositerType;
    public String accountNumber;
    public int amount;
}
