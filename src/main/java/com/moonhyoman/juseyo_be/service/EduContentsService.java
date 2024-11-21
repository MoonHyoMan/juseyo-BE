package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.EduContents;
import com.moonhyoman.juseyo_be.dto.EduContentsDto;
import com.moonhyoman.juseyo_be.exception.CustomException;
import com.moonhyoman.juseyo_be.exception.ErrorCode;
import com.moonhyoman.juseyo_be.repository.EduContentsRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class EduContentsService {
    @Value("${youtube.playlist-url}")
    private String playlistUrl;

    private final EduContentsRepository eduContentsRepository;

    public List<EduContents> findContentsByEduType(String eduType) {
        return eduContentsRepository.findAllByEduType(eduType);
    }
    @Cacheable(value = "crawledVideos", key = "'playlist_' + #playlistUrl")
    public List<EduContentsDto> fetchYoutubeVideos() {
        WebDriverManager.chromedriver().browserVersion("131.0").setup();

        // 크롬 브라우저 설정
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless"); // 브라우저 창을 띄우지 않음
        options.addArguments("--disable-gpu"); // GPU 비활성화
        options.addArguments("--no-sandbox"); // 리눅스 환경에서 필요할 수 있음

        // ChromeDriver 생성
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(playlistUrl);

            // 유튜브 재생목록에서 동영상 요소 탐색
            List<WebElement> videoElements = driver.findElements(By.cssSelector("a#video-title"));

            // 병렬 처리를 위한 스레드 풀 생성
            ExecutorService executor = Executors.newFixedThreadPool(4); // 4개의 스레드 사용
            List<Future<EduContentsDto>> futures = new ArrayList<>();

            for (WebElement video : videoElements) {
                futures.add(executor.submit(() -> {
                    String title = video.getAttribute("title");
                    String link = "https://www.youtube.com" + video.getAttribute("href");

                    return EduContentsDto.builder()
                            .eduType("videos")
                            .category("금융")
                            .titles(title)
                            .link(link)
                            .quizzes(new ArrayList<>()) // 퀴즈는 비워둠
                            .build();
                }));
            }

            // 결과를 수집
            List<EduContentsDto> videoList = new ArrayList<>();
            for (Future<EduContentsDto> future : futures) {
                try {
                    videoList.add(future.get());
                } catch (Exception e) {
                    e.printStackTrace(); // 개별 오류 출력
                }
            }

            executor.shutdown(); // 스레드 풀 종료
            return videoList;
        } finally {
            driver.quit(); // WebDriver 종료
        }
    }
    public  EduContents findConentsById(int id) {
        EduContents eduContents = eduContentsRepository.findById(id);
        if(eduContents == null){
            throw new CustomException(ErrorCode.CONTENTS_NOT_FOUND);
        }
        return eduContents;
    }


}
