package com.moonhyoman.juseyo_be.service;

import com.moonhyoman.juseyo_be.domain.Child;
import com.moonhyoman.juseyo_be.domain.Parent;
import com.moonhyoman.juseyo_be.repository.ChildRepository;
import com.moonhyoman.juseyo_be.repository.ParentRepository;
import com.moonhyoman.juseyo_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    public Optional<?> getUserById(String id) {
        Optional<Child> child = childRepository.findById(id);
        if(!child.isEmpty()) {
            return child;
        }
        return parentRepository.findById(id);
    }
}