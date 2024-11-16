package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.Mission;
import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.repository.MissionRepository;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
}
