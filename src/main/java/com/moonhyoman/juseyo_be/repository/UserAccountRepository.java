package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByDepositer(String depositer);
}
