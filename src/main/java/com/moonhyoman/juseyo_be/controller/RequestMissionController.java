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
@RequestMapping("/api/missions")
@Tag(name = "mission", description = "미션 API")
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

    @PostMapping("/request")
    @Operation(summary = "미션 요청", description = "jwt 필요")
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
}
