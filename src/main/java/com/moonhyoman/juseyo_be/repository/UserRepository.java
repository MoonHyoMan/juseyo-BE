package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByIdAndType(String id, String type);
    Optional<User> findByParentId(String id);
}