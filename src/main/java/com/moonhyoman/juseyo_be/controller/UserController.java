package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.LoginRequest;
import com.moonhyoman.juseyo_be.security.jwt.JwtGenerator;
import com.moonhyoman.juseyo_be.security.jwt.JwtToken;
import com.moonhyoman.juseyo_be.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public UserController(UserService userService, JwtGenerator jwtGenerator) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest LoginRequest) {
        User user = userService.login(LoginRequest);
        log.info("user: {}", user.getId());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
        }
        // JWT 생성
        JwtToken jwt = jwtGenerator.generateToken(user.getId());

        // JWT를 포함한 응답
        return ResponseEntity.ok(jwt);
    }

    @GetMapping("/token-test")
    public String testToken(Authentication authentication) {
        String id = authentication.getName();
        System.out.println(id);
        return id;
    }
}