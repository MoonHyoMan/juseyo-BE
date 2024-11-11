package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.ChildLevel;
import com.moonhyoman.juseyo_be.repository.ChildLevelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChildLevelService {
    @Autowired
    private ChildLevelRepository childLevelRepository;

    public void insertChildLevel(String id) {
        ChildLevel childLevel = ChildLevel.builder().
                id(id)
                .level(1)
                .exp(0)
                .build();

        childLevelRepository.save(childLevel);
        log.info("childLevel이 성공적으로 저장되었습니다: {}", childLevel);
    }
}
