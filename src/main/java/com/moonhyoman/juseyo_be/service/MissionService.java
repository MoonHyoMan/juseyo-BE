package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.CompleteMission;
import com.moonhyoman.juseyo_be.domain.FailMission;
import com.moonhyoman.juseyo_be.domain.Mission;
import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.repository.CompleteMissionRepository;
import com.moonhyoman.juseyo_be.repository.FailMissionRepository;
import com.moonhyoman.juseyo_be.repository.MissionRepository;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MissionService {
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompleteMissionRepository completeMissionRepository;
    @Autowired
    private FailMissionRepository failMissionRepository;

    public List<Mission> getAllMission(String userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        List<Mission> missionList;
        if(user.getType().equals("parent")){
            missionList = missionRepository.findAllByParentId(userId);
        }else{
            missionList = missionRepository.findAllByChildId(userId);
        }

        return missionList;
    }

    public void completeMission(Long id, String userId){
        Optional<Mission> optionalMission = missionRepository.findByIdAndParentId(id, userId);
        if(optionalMission.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 미션 id입니다.");
        }
        Mission mission = optionalMission.get();


        LocalDateTime now = LocalDateTime.now();

        // 원하는 형식으로 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        CompleteMission completeMission = CompleteMission.builder()
                .id(mission.getId())
                .childId(mission.getChildId())
                .parentId(mission.getParentId())
                .startDate(mission.getStartDate())
                .endDate(mission.getEndDate())
                .content(mission.getContent())
                .point(mission.getPoint())
                .category(mission.getCategory())
                .doneDate(now.format(formatter))
                .build();

        completeMissionRepository.save(completeMission);

        missionRepository.delete(mission);
    }

    public void failMission(Long id, String userId){
        Optional<Mission> optionalMission = missionRepository.findByIdAndParentId(id, userId);
        if(optionalMission.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 미션 id입니다.");
        }
        Mission mission = optionalMission.get();


        LocalDateTime now = LocalDateTime.now();

        // 원하는 형식으로 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        FailMission failMission = FailMission.builder()
                .id(mission.getId())
                .childId(mission.getChildId())
                .parentId(mission.getParentId())
                .startDate(mission.getStartDate())
                .endDate(mission.getEndDate())
                .content(mission.getContent())
                .point(mission.getPoint())
                .category(mission.getCategory())
                .failDate(now.format(formatter))
                .build();

        failMissionRepository.save(failMission);

        missionRepository.delete(mission);
    }
}
