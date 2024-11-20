package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.domain.UserAccount;
import com.moonhyoman.juseyo_be.dto.ProfileResponse;
import com.moonhyoman.juseyo_be.dto.PointResponse;
import com.moonhyoman.juseyo_be.dto.MissionCountResponse;
import com.moonhyoman.juseyo_be.dto.AccountNumberResponse;
import com.moonhyoman.juseyo_be.repository.SuccessMissionRepository;
import com.moonhyoman.juseyo_be.repository.UserAccountRepository;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class MyPageService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SuccessMissionRepository successMissionRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    public ProfileResponse getProfile(String userId) {
        // User 데이터 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 성공한 미션 횟수 조회
        int successCount = successMissionRepository.countByChildId(userId);

        // ProfileResponse에 데이터 매핑
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setName(user.getName());
        profileResponse.setTotalPoint(user.getPoint()); // 누적 금액(원)
        profileResponse.setSuccessCount(successCount);

        // 레벨 계산 로직 (미션 성공 횟수 기반)
        String level;
        if (successCount < 10) {
            level = "Lv.1 똑똑한 첫걸음";
        } else if (successCount < 30) {
            level = "Lv.2 똑똑한 두걸음";
        } else {
            level = "Lv.3 똑똑한 세걸음";
        }
        profileResponse.setLevel(level);

        return profileResponse;
    }

    public PointResponse getTotalPoints(String userId) {
        // User 데이터 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 적립 금액 반환
        return new PointResponse(user.getPoint());
    }

    public MissionCountResponse getCompletedMissionCount(String userId) {
        // 완료한 미션 횟수 조회
        int completedMissions = successMissionRepository.countByChildId(userId);

        // 완료한 미션 수 반환
        return new MissionCountResponse(completedMissions);
    }

    public AccountNumberResponse getAccountNumber(String userId) {
        // User 데이터 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 계좌 번호 반환
        return new AccountNumberResponse(user.getAccountNum());
    }

    public void chargePoint(String userId, int point){
        UserAccount userAccount = userAccountRepository.findByDepositer(userId);

        Boolean result = userAccount.minusAmount(point);
        if(!result) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        userAccountRepository.save(userAccount);

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        user.chargePoint(point);

        userRepository.save(user);
    }
    public void withdrawPoint(String userId, int point){
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        boolean result = user.withdrawPoint(point);

        if(!result){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UserAccount userAccount = userAccountRepository.findByDepositer(userId);

        userAccount.plusAmount(point);

        userAccountRepository.save(userAccount);
        userRepository.save(user);
    }
}
