package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.RequestMission;
import com.moonhyoman.juseyo_be.dto.RequestMissionRequest;
import com.moonhyoman.juseyo_be.service.RequestMissionService;
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
@Tag(name = "Request Mission", description = "요청된 미션 관리(승인, 거절, 성공, 실패 등) API")
public class RequestMissionController {
    private final RequestMissionService requestMissionService;

    @Autowired
    public RequestMissionController(RequestMissionService requestMissionService) {
        this.requestMissionService = requestMissionService;
    }

    @GetMapping("/all-list")
    public ResponseEntity<List<RequestMission>> getAllRequestMissions(Authentication authentication) {
        List<RequestMission> requestMissionList = requestMissionService.getAllRequestMission(authentication.getName());

        return ResponseEntity.ok(requestMissionList);
    }

    @PostMapping("/request")
    public ResponseEntity requestMission(Authentication Authentication, @RequestBody RequestMissionRequest requestMissionRequest) {
        log.info("Request Mission Request: {}", Authentication.getName());
        Boolean result = requestMissionService.addRequestMission(Authentication.getName(), requestMissionRequest);

        if (!result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.ok().build();
    }
}
