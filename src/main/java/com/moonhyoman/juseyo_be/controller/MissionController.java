package com.moonhyoman.juseyo_be.controller;

import com.example.allowance.entity.Mission;
import com.example.allowance.service.MissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @PostMapping("/request")
    public ResponseEntity<Mission> requestMission(@RequestBody Mission mission) {
        return ResponseEntity.ok(missionService.requestMission(mission));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Mission>> getPendingMissions() {
        return ResponseEntity.ok(missionService.getPendingMissions());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Mission> approveMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.approveMission(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Mission> rejectMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.rejectMission(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Mission> completeMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.completeMission(id));
    }

    @PutMapping("/{id}/fail")
    public ResponseEntity<Mission> failMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.failMission(id));
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<Mission>> getInProgressMissions(@RequestParam Long userId) {
        return ResponseEntity.ok(missionService.getInProgressMissions(userId));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Mission>> getCompletedMissions(@RequestParam Long userId) {
        return ResponseEntity.ok(missionService.getCompletedMissions(userId));
    }

    @GetMapping("/top")
    public ResponseEntity<List<Mission>> getTopMissions(@RequestParam Long userId) {
        return ResponseEntity.ok(missionService.getTopMissions(userId));
    }
}
