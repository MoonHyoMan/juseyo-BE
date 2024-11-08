package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.LoginRequest;
import com.moonhyoman.juseyo_be.dto.SignupRequest;
import com.moonhyoman.juseyo_be.security.jwt.JwtGenerator;
import com.moonhyoman.juseyo_be.security.jwt.JwtToken;
import com.moonhyoman.juseyo_be.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthService authService, JwtGenerator jwtGenerator) {
        this.authService = authService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "기본 로그인 api")
    public ResponseEntity login(@RequestBody LoginRequest LoginRequest) {
        User user = authService.login(LoginRequest);
//        log.info("user: {}", user.getId());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
        }
        // JWT 생성
        JwtToken jwt = jwtGenerator.generateToken(user.getId());

        // JWT를 포함한 응답
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "기본 회원가입 api")
    @Parameters({
            @Parameter(name = "id", description = "아이디", example = "chrome123@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "abcd1234"),
            @Parameter(name = "name", description = "이름", example = "김춘배"),
            @Parameter(name = "accountNum", description = "계좌번호 더미라 대충 보내도 ㄱㅊ", example = "1234-5678"),
            @Parameter(name = "type", description = "자식인지(child), 부모인지(parent)", example = "child"),
            @Parameter(name = "point", description = "필수값 아님.<br>" +
                    "넣으려면 0이나 아무값 ㄱ", example = "0"),
            @Parameter(name = "parentId", description = "필수값 아님.<br>" +
                    "근데 자식 회원가입이면 넣어야 함." +
                    "<br>부모 아이디를 넣어야 함.", example = "admin")
    })
    public ResponseEntity signup(@RequestBody SignupRequest signupRequest){
        if(!signupRequest.getType().equals("child") || !signupRequest.getType().equals("parent")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type에는 child나 parent만 입력 가능합니다.");
        }

        User user = authService.signup(signupRequest);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
        }

        return ResponseEntity.ok().build();
    }

    // 아이디 중복 체크
    @GetMapping("/id-duplicate")
    @Operation(summary = "아이디 중복체크",
            description = "아이디 중복체크 API<br>" +
                    "무조건 200 리턴함.<br>" +
                    "아이디가 있으면 body에 exist를 담아서 보냄.")
    public ResponseEntity checkIdDuplicate(@RequestParam String id) {
        boolean exists = authService.idDuplicate(id);

        if(exists){
            return ResponseEntity.ok("exist");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token-test")
    public String testToken(Authentication authentication) {
        String id = authentication.getName();
        System.out.println(id);
        return id;
    }
}