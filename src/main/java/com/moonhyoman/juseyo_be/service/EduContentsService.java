package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.EduContents;
import com.moonhyoman.juseyo_be.exception.CustomException;
import com.moonhyoman.juseyo_be.exception.ErrorCode;
import com.moonhyoman.juseyo_be.repository.EduContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EduContentsService {
    private final EduContentsRepository eduContentsRepository;

    public List<EduContents> findContentsByEduType(String eduType) {
        return eduContentsRepository.findAllByEduType(eduType);
    }

    public  EduContents findConentsById(int id) {
        EduContents eduContents = eduContentsRepository.findById(id);
        if(eduContents == null){
            throw new CustomException(ErrorCode.CONTENTS_NOT_FOUND);
        }
        return eduContents;
    }
}
