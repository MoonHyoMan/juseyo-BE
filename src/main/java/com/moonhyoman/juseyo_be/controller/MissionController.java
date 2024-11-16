package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.Mission;
import com.moonhyoman.juseyo_be.service.MissionService;
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

    @GetMapping("/all-list")
    public ResponseEntity<List<Mission>> getAllMission(Authentication authentication) {
        List<Mission> missionList = missionService.getAllMission(authentication.getName());
        return ResponseEntity.ok(missionList);
    }

    @GetMapping("/parent/complete/{id}")
    public ResponseEntity completeMission(Authentication authentication,@PathVariable Long id){
//        log.info("id: {}", id);
        missionService.completeMission(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/parent/fail/{id}")
    public ResponseEntity failMission(Authentication authentication,@PathVariable Long id){
        missionService.failMission(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
