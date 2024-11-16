package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.CompleteMission;
import com.moonhyoman.juseyo_be.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompleteMissionRepository extends JpaRepository<CompleteMission, Long> {
    List<CompleteMission> findAllByParentId(String parentId);
    List<CompleteMission> findAllByChildId(String childId);
}
