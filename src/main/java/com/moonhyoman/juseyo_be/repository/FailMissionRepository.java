package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.FailMission;
import com.moonhyoman.juseyo_be.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FailMissionRepository extends JpaRepository<FailMission, Long> {
    List<FailMission> findAllByParentId(String parentId);
    List<FailMission> findAllByChildId(String childId);
}
