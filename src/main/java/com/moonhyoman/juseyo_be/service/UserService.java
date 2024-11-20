package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.UserResponse;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getMyInfo(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword()) // 보안을 위해 필요에 따라 암호화 처리 가능
                .accountNum(user.getAccountNum())
                .type(user.getType())
                .build();
    }
}
