package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.RequestMission;
import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.RequestMissionRequest;
import com.moonhyoman.juseyo_be.repository.RequestMissionRepository;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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
        User user = optionalUser.get();
        if(!user.getType().equals("child")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자식만 미션 요청이 가능합니다.");
        }
        String parentId = user.getParentId();

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

    public List<RequestMission> getAllRequestMission(String id){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        List<RequestMission> requestMissionList;
        if(user.getType().equals("parent")){
            requestMissionList = requestMissionRepository.findAllByParentId(user.getParentId());
        }else {
            requestMissionList = requestMissionRepository.findAllByChildId(id);
        }

        return requestMissionList;
    }
}
