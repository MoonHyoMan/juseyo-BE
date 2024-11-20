package com.moonhyoman.juseyo_be.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String depositer;
    public String accountNum;
    public int amount;

    @Transactional
    public void plusAmount(int amount){
        this.amount = this.amount + amount;
    }

    @Transactional
    public Boolean minusAmount(int amount){
        if(this.amount < amount){
            return false;
        }
        this.amount = this.amount - amount;

        return true;
    }
}
