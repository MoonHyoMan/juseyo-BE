package com.moonhyoman.juseyo_be.repository;

import com.moonhyoman.juseyo_be.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, String> {
}
