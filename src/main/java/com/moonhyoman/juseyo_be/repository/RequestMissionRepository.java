package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.RequestMission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestMissionRepository extends CrudRepository<RequestMission, Long> {
    List<RequestMission> findAllByParentId(String parentId);
    List<RequestMission> findAllByChildId(String childId);
}
