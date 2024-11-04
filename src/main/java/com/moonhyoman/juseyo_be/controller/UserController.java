package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.Parent;
import com.moonhyoman.juseyo_be.dto.LoginRequest;
import com.moonhyoman.juseyo_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Optional<?> getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest LoginRequest) {
        System.out.println(LoginRequest.getId());
        System.out.println(LoginRequest.getPwd());
        return "good";
    }
}