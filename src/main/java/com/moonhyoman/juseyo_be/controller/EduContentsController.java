package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.EduContents;
import com.moonhyoman.juseyo_be.dto.EduContentsDto;
import com.moonhyoman.juseyo_be.service.EduContentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


/**
 * - GET /education/videos : 교육 영상 목록 조회
 * - GET /education/articles : 뉴스 기사 목록 조회
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/education")
@Tag(name = "EducationContents", description = "교육 컨텐츠 조회 API(영상,뉴스)")
public class EduContentsController {
    private final EduContentsService eduContentsService;

    @GetMapping("/videos")
    @Operation(summary = "교육 영상 목록 조회", description = "교육 비디오 조회")
    public ResponseEntity<List<EduContentsDto>> getAllVideos() {// ResponseEntity<List<EduContents>>
//        List<EduContents> videoList = eduContentsService.findContentsByEduType("videos");
//        return ResponseEntity.ok(videoList);
        return ResponseEntity.ok(eduContentsService.fetchYoutubeVideos());
    }


    @GetMapping("/articles")
    @Operation(summary = "교육 뉴스 기사 목록 조회", description = "교육 뉴스 기사 조회")
    public ResponseEntity<List<EduContents>> getAllArticles() {
        List<EduContents> videoList = eduContentsService.findContentsByEduType("articles");
        return ResponseEntity.ok(videoList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "교육 컨텐츠 조회", description = "컨텐츠 개별 조회")
    public ResponseEntity<EduContents> getAllVideos(@PathVariable int id) {
        EduContents contents = eduContentsService.findConentsById(id);
        return ResponseEntity.ok(contents);
    }
}
