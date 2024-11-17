package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.EduContents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EduContentsRepository extends JpaRepository<EduContents, String> {
    List<EduContents> findAllByEduType(String eduType);
    EduContents findById(int id);
}
