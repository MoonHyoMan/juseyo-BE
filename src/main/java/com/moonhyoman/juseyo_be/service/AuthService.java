package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.LoginRequest;
import com.moonhyoman.juseyo_be.dto.PasswordChangeRequest;
import com.moonhyoman.juseyo_be.dto.SignupRequest;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@PropertySource("classpath:application.yml")
public class AuthService {

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

    public User signup(SignupRequest req) throws Exception {
        // 동일 ID가 있는지 확인
        if (userRepository.existsById(req.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");
        }
        if(req.getType().equals("child")&&userRepository.findByIdAndType(req.getParentId(), "parent").isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "부모 아이디가 존재하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(req.getPassword());

        // 새 User 객체 생성
        User newUser = User.builder()
                .id(req.getId())
                .password(encodedPassword)
                .name(req.getName())
                .accountNum(req.getAccountNum())
                .type(req.getType())
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

    @Transactional
    public Boolean passwordChange(String id, PasswordChangeRequest passwordChangeRequest){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        if(!passwordEncoder.matches(passwordChangeRequest.getNowPassword(), user.getPassword())) {
            return false;
        }

        // 비밀번호 변경
        String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getChangePassword());
        user.changePassword(encodedPassword);

        // 변경 후 다시 사용자를 조회하여 비밀번호가 변경되었는지 확인
        Optional<User> updatedUser = userRepository.findById(id);

        // 비밀번호가 변경되었는지 확인
        if (updatedUser.isPresent() && passwordEncoder.matches(passwordChangeRequest.getChangePassword(), updatedUser.get().getPassword())) {
            log.info("Password changed successfully for user: {}", id);
            return true;
        } else {
            log.error("Failed to change password for user: {}", id);
            return false;
        }
    }

    public User findById(String id){
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();

        return user;
    }

    @Transactional
    public void linkSocial(String id, String social){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();

        user.linkSocial(social);
    }
}