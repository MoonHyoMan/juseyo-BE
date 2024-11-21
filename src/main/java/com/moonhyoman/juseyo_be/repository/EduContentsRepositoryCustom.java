package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.EduContents;

import java.util.List;

public interface EduContentsRepositoryCustom {
    List<EduContents> findAllByEduType(String eduType);
}
