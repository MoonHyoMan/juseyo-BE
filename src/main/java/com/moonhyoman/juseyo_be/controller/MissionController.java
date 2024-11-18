package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.CompleteMission;
import com.moonhyoman.juseyo_be.domain.FailMission;
import com.moonhyoman.juseyo_be.domain.Mission;
import com.moonhyoman.juseyo_be.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mission")
@Tag(name = "미션 API", description = "미션 관련 API<br>" +
        "/parent는 부모만 사용 가능한 API<br>" +
        "/child는 자식만 사용 가능한 API")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/progress-list")
    @Operation(summary = "진행중인 미션리스트 조회", description = "JWT 필요")
    public ResponseEntity<List<Mission>> getAllProgressMission(Authentication authentication) {
        List<Mission> missionList = missionService.getAllProgressMission(authentication.getName());
        return ResponseEntity.ok(missionList);
    }

    @GetMapping("/complete-list")
    @Operation(summary = "성공한 미션리스트 조회", description = "JWT 필요")
    public ResponseEntity<List<CompleteMission>> getAllCompleteMission(Authentication authentication) {
        List<CompleteMission> completeMissionList = missionService.getAllCompleteMission(authentication.getName());
        return ResponseEntity.ok(completeMissionList);
    }

    @GetMapping("/fail-list")
    @Operation(summary = "실패한 미션리스트 조회", description = "JWT 필요")
    public ResponseEntity<List<FailMission>> getAllFailMission(Authentication authentication) {
        List<FailMission> failMissionList = missionService.getAllFailMission(authentication.getName());
        return ResponseEntity.ok(failMissionList);
    }

    @GetMapping("/parent/complete/{id}")
    @Operation(summary = "미션 성공 처리", description = "JWT 필요<br>" +
            "id는 미션의 id입니다.<br>" +
            "부모만 호출 가능한 API")
    public ResponseEntity completeMission(Authentication authentication,@PathVariable Long id){
//        log.info("id: {}", id);
        missionService.completeMission(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/parent/fail/{id}")
    @Operation(summary = "미션 실패 처리", description = "JWT 필요<br>" +
            "id는 미션의 id입니다.<br>" +
            "부모만 호출 가능한 API")
    public ResponseEntity failMission(Authentication authentication,@PathVariable Long id){
        missionService.failMission(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
