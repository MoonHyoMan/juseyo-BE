package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.Mission;
import com.moonhyoman.juseyo_be.domain.RejectRequestMission;
import com.moonhyoman.juseyo_be.domain.RequestMission;
import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.RequestMissionRequest;
import com.moonhyoman.juseyo_be.repository.MissionRepository;
import com.moonhyoman.juseyo_be.repository.RejectRequestMissionRepository;
import com.moonhyoman.juseyo_be.repository.RequestMissionRepository;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private RejectRequestMissionRepository rejectRequestMissionRepository;

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

    public void approveMission(Long id, String userId){
        Optional<RequestMission> optionalRequestMission = requestMissionRepository.findByIdAndChildId(id, userId);
        if(optionalRequestMission.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 미션 ID입니다.");
        }
        RequestMission requestMission = optionalRequestMission.get();
        Mission mission = Mission.builder()
                .id(requestMission.getId())
                .childId(requestMission.getChildId())
                .parentId(requestMission.getParentId())
                .startDate(requestMission.getStartDate())
                .endDate(requestMission.getEndDate())
                .content(requestMission.getContent())
                .point(requestMission.getPoint())
                .category(requestMission.getCategory())
                .build();


        missionRepository.save(mission);

        requestMissionRepository.delete(requestMission);

        log.info("request mission approved request_mission->mission");
    }

    public void rejectMission(Long id, String userId){
        Optional<RequestMission> optionalRequestMission = requestMissionRepository.findByIdAndChildId(id, userId);
        if(optionalRequestMission.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 미션 ID입니다.");
        }
        RequestMission requestMission = optionalRequestMission.get();
        RejectRequestMission rejectRequestMission = RejectRequestMission.builder()
                .id(requestMission.getId())
                .childId(requestMission.getChildId())
                .parentId(requestMission.getParentId())
                .startDate(requestMission.getStartDate())
                .endDate(requestMission.getEndDate())
                .content(requestMission.getContent())
                .point(requestMission.getPoint())
                .category(requestMission.getCategory())
                .build();


        rejectRequestMissionRepository.save(rejectRequestMission);

        requestMissionRepository.delete(requestMission);

        log.info("request mission rejected request_mission->mission");
    }
}
