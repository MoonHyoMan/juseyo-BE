package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.dto.AccountNumberResponse;
import com.moonhyoman.juseyo_be.dto.MissionCountResponse;
import com.moonhyoman.juseyo_be.dto.PointResponse;
import com.moonhyoman.juseyo_be.dto.ProfileResponse;
import com.moonhyoman.juseyo_be.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/point")
    @Operation(summary = "적립 금액 조회", description = "사용자의 총 적립 금액을 조회")
    public ResponseEntity<PointResponse> getTotalPoints(Authentication authentication) {
        String userId = authentication.getName(); // JWT로부터 사용자 ID 추출
        PointResponse response = myPageService.getTotalPoints(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/point/charge/{point}")
    @Operation(summary = "포인트 충전 API", description = "계좌에서 포인트로 충전")
    public ResponseEntity chargePoint(Authentication authentication, @PathVariable int point){
        myPageService.chargePoint(authentication.getName(), point);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/point/withdraw/{point}")
    @Operation(summary = "포인트 인출 API", description = "포인트에서 계좌로 입금")
    public ResponseEntity withdrawPoint(Authentication authentication, @PathVariable int point){
        myPageService.withdrawPoint(authentication.getName(), point);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/completed-missions")
    @Operation(summary = "완료한 미션 수 조회", description = "사용자가 완료한 미션 횟수를 조회")
    public ResponseEntity<MissionCountResponse> getCompletedMissions(Authentication authentication) {
        String userId = authentication.getName();
        MissionCountResponse response = myPageService.getCompletedMissionCount(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account-number")
    @Operation(summary = "계좌 번호 조회", description = "사용자의 계좌 번호를 조회")
    public ResponseEntity<AccountNumberResponse> getAccountNumber(Authentication authentication) {
        String userId = authentication.getName();
        AccountNumberResponse response = myPageService.getAccountNumber(userId);
        return ResponseEntity.ok(response);
    }
}
