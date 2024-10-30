package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}