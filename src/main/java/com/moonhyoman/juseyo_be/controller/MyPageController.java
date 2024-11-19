package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.dto.ProfileResponse;
import com.moonhyoman.juseyo_be.service.MyPageService;
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
@RequestMapping("/api/mypage")
@Tag(name = "MyPage", description = "마이페이지 관련 API")
public class MyPageController {
    private final MyPageService myPageService;

    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/profile")
    @Operation(
            summary = "프로필 조회",
            description = "사용자 이름, 총 적립 금액(원), 성공 미션 횟수, 레벨 정보 조회."
    )
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
        String userId = authentication.getName(); // JWT로부터 사용자 ID 추출
        ProfileResponse profile = myPageService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }
}
