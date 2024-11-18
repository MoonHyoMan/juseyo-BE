package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.Mission;
import com.moonhyoman.juseyo_be.domain.RequestMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findAllByParentId(String parentId);
    List<Mission> findAllByChildId(String childId);
    Optional<Mission> findByIdAndParentId(Long id, String parentId);
}
