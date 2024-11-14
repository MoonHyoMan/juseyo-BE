package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.RequestMission;
import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.RequestMissionRequest;
import com.moonhyoman.juseyo_be.repository.RequestMissionRepository;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RequestMissionService {
    @Autowired
    private RequestMissionRepository requestMissionRepository;
    @Autowired
    private UserRepository userRepository;

    public Boolean addRequestMission(String id, RequestMissionRequest requestMissionRequest) {
        Optional<User> optionalUser = userRepository.findById(id);
        String parentId = optionalUser.get().getParentId();

        RequestMission requestMission = RequestMission.builder()
                .childId(id)
                .parentId(parentId)
                .startDate(requestMissionRequest.getStartDate())
                .endDate(requestMissionRequest.getEndDate())
                .content(requestMissionRequest.getContent())
                .category(requestMissionRequest.getCategory())
                .point(requestMissionRequest.getPoint())
                .build();

        requestMissionRepository.save(requestMission);
        log.info("요청 미션이 잘 저장되었습니다. {}", requestMission);
        return true;
    }
}
