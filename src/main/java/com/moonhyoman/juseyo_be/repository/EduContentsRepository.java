package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.EduContents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EduContentsRepository extends JpaRepository<EduContents, String>,EduContentsRepositoryCustom {
    EduContents findById(int id);
    List<EduContents> findAllByEduType(String eduType);
    boolean existsByLink(String link);//중복확인
}
