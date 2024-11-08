package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.LoginRequest;
import com.moonhyoman.juseyo_be.dto.SignupRequest;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findById(req.getId());

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }


    public User signup(SignupRequest req) {
        // 동일 ID가 있는지 확인
        if (userRepository.existsById(req.getId())) {
            return null; // 이미 존재하는 ID인 경우 null 반환 (예외 처리로 대체 가능)
        }

        String encodedPassword = passwordEncoder.encode(req.getPassword());

        // 새 User 객체 생성
        User newUser = User.builder()
                .id(req.getId())
                .password(encodedPassword)
                .name(req.getName())
                .accountNum(req.getAccountNum())
                .type(req.getType())
                .point(req.getPoint() != null ? req.getPoint() : 0)
                .parentId(req.getParentId())
                .build();

        // User 객체 저장
        return userRepository.save(newUser);
    }

    public Boolean idDuplicate(String id){
        if(userRepository.existsById(id)){
            return true;
        }
        return false;
    }
}