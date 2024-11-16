package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.RequestMission;
import com.moonhyoman.juseyo_be.dto.RequestMissionRequest;
import com.moonhyoman.juseyo_be.service.RequestMissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/request-missions")
@Tag(name = "요청 미션 API", description = "요청 미션 관련 API<br>" +
        "/parent는 부모만 사용 가능한 API<br>" +
        "/child는 자식만 사용 가능한 API")
public class RequestMissionController {
    private final RequestMissionService requestMissionService;

    @Autowired
    public RequestMissionController(RequestMissionService requestMissionService) {
        this.requestMissionService = requestMissionService;
    }

    @GetMapping("/all-list")
    @Operation(summary = "요청된 미션리스트 조회", description = "jwt 필요")
    public ResponseEntity<List<RequestMission>> getAllRequestMissions(Authentication authentication) {
        List<RequestMission> requestMissionList = requestMissionService.getAllRequestMission(authentication.getName());

        return ResponseEntity.ok(requestMissionList);
    }

    @PostMapping("/child/request")
    @Operation(summary = "미션 요청", description = "자녀만 사용 가능한 API")
    @Parameters({
            @Parameter(name = "startDate", description = "시작 날짜 시간", example = "2024-11-15 12:00:00"),
            @Parameter(name = "endDate", description = "마감 날짜 시간", example = "2024-11-30 12:00:00"),
            @Parameter(name = "content", description = "미션 내용", example = "설거지하기"),
            @Parameter(name = "category", description = "미션 카테고리", example = "집안일"),
            @Parameter(name = "point", description = "용돈 금액", example = "1000")})
    public ResponseEntity requestMission(Authentication Authentication, @RequestBody RequestMissionRequest requestMissionRequest) {
        log.info("Request Mission Request: {}", Authentication.getName());
        Boolean result = requestMissionService.addRequestMission(Authentication.getName(), requestMissionRequest);

        if (!result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/parent/approve/{id}")
    @Operation(summary = "요청 미션 허가 API", description = "요청된 미션을 허가하는 API<br>" +
            "id는 요청미션의 id입니다.<br>" +
            "부모만 사용할 수 있는 API")
    public ResponseEntity approveRequestMission(Authentication authentication, @PathVariable Long id){
//        log.info("mission id: {}", id);
        requestMissionService.approveMission(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/parent/reject/{id}")
    @Operation(summary = "요청 미션 거절 API", description = "요청된 미션을 거절하는 API<br>" +
            "id는 요청미션의 id입니다.<br>" +
            "부모만 사용할 수 있는 API")
    public ResponseEntity rejectRequestMission(Authentication authentication, @PathVariable Long id){
        requestMissionService.rejectMission(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
