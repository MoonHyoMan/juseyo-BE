package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.SuccessMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuccessMissionRepository extends JpaRepository<SuccessMission, Long> {
    // 자녀 ID로 성공 미션 횟수 조회
    int countByChildId(String childId);
}
