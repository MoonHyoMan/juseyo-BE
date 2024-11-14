package com.moonhyoman.juseyo_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "회원 정보 API(정보 조회, 수정 등")
public class UsersController {



}
