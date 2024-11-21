package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.dto.UserResponse;
import com.moonhyoman.juseyo_be.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "회원 정보 API(정보 조회, 수정 등)")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "현재 사용자의 이름, 비밀번호, 계좌번호, 부모/자녀 구분 정보를 조회")
    public ResponseEntity<UserResponse> getMyInfo(Authentication authentication) {
        String userId = authentication.getName(); // JWT에서 사용자 ID 추출
        UserResponse userResponse = userService.getMyInfo(userId);
        return ResponseEntity.ok(userResponse);
    }
}