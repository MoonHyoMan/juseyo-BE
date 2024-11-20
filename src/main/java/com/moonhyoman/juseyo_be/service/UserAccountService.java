package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.UserAccount;
import com.moonhyoman.juseyo_be.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    public void insertUserAccount(String id, String type, String accountNum){
        UserAccount userAccount = UserAccount.builder()
                .depositer(id)
                .accountNum(accountNum)
                .amount(type.equals("child") ? 0 : 1000000)
                .build();

        userAccountRepository.save(userAccount);
        log.info("UserAccount가 성공적으로 저장되었습니다: {}", userAccount);
    }

}
