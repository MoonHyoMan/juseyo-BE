package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}