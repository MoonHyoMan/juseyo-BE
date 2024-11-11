package com.moonhyoman.juseyo_be.controller;

import com.moonhyoman.juseyo_be.domain.User;
import com.moonhyoman.juseyo_be.dto.LoginRequest;
import com.moonhyoman.juseyo_be.dto.PasswordChangeRequest;
import com.moonhyoman.juseyo_be.dto.SignupRequest;
import com.moonhyoman.juseyo_be.security.jwt.JwtGenerator;
import com.moonhyoman.juseyo_be.security.jwt.JwtToken;
import com.moonhyoman.juseyo_be.service.AuthService;
import com.moonhyoman.juseyo_be.service.ChildLevelService;
import com.moonhyoman.juseyo_be.service.KakaoLoginService;
import com.moonhyoman.juseyo_be.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "로그인 및 회원가입 관련 API")
public class AuthController {
    private final AuthService authService;
    private final KakaoLoginService kakaoLoginService;
    private final ChildLevelService childLevelService;
    private final UserAccountService userAccountService;

    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthService authService, KakaoLoginService kakaoLoginService, ChildLevelService childLevelService, UserAccountService userAccountService, JwtGenerator jwtGenerator) {
        this.authService = authService;
        this.kakaoLoginService = kakaoLoginService;
        this.childLevelService = childLevelService;
        this.userAccountService = userAccountService;

        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "기본 로그인 api")
    public ResponseEntity login(@RequestBody LoginRequest LoginRequest) {
        User user = authService.login(LoginRequest);
//        log.info("user: {}", user.getId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
        }
        // JWT 생성
        JwtToken jwt = jwtGenerator.generateToken(user.getId());

        // JWT를 포함한 응답
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "기본 회원가입 api")
    @Parameters({@Parameter(name = "id", description = "아이디", example = "chrome123@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "abcd1234"),
            @Parameter(name = "name", description = "이름", example = "김춘배"),
            @Parameter(name = "accountNum", description = "계좌번호 더미라 대충 보내도 ㄱㅊ", example = "1234-5678"),
            @Parameter(name = "type", description = "자식인지(child), 부모인지(parent)", example = "child"),
            @Parameter(name = "parentId", description = "필수값X<br>" + "근데 자식 회원가입이면 넣어야 함." + "<br>부모 아이디를 넣어야 함.", example = "admin"),
            @Parameter(name = "social", description = "필수값X<br>" + "kakao or naver 넣어주면 됨")})
    public ResponseEntity signup(@RequestBody SignupRequest signupRequest) throws Exception {
        if (!signupRequest.getType().equals("child") && !signupRequest.getType().equals("parent")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type에는 child나 parent만 입력 가능합니다.");
        }
        if (signupRequest.getType().equals("child")) {
            if (signupRequest.getParentId() == null || signupRequest.getParentId().equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("자식으로 회원가입시 parent_id는 필수입니다.");
            }
            childLevelService.insertChildLevel(signupRequest.getId());
        }

        User user = authService.signup(signupRequest);

        userAccountService.insertUserAccount(signupRequest.getId(), signupRequest.getType(), signupRequest.getAccountNum());

        return ResponseEntity.ok().build();
    }

    // 아이디 중복 체크
    @GetMapping("/id-duplicate")
    @Operation(summary = "아이디 중복체크", description = "아이디 중복체크 API<br>" + "무조건 200 리턴함.<br>" + "아이디가 있으면 body에 exist를 담아서 보냄.")
    public ResponseEntity checkIdDuplicate(@RequestParam String id) {
        boolean exists = authService.idDuplicate(id);

        if (exists) {
            return ResponseEntity.ok("exist");
        }
        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경
    @PutMapping("/password-change")
    @Operation(summary = "비밀번호 변경(JWT 필요)", description = "비밀번호 변경 API<br>" + "jwt 필요한 api")
    @Parameters({@Parameter(name = "nowPassword", description = "현재 비밀번호", example = "nowPassword"), @Parameter(name = "changePassword", description = "변경할 비밀번호", example = "changePassword"),})
    public ResponseEntity passwordChange(Authentication authentication, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        Boolean result = authService.passwordChange(authentication.getName(), passwordChangeRequest);
        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하기 않습니다.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/kakao")
    @Operation(summary = "kakao 로그인", description = "kakao 로그인 API<br>" + "카카오 인가코드를 보내고<br>" + "아이디가 있다면 토큰 발급<br>" + "없으면 400에러와 로그인 실패 return")
    @Parameters({@Parameter(name = "code", description = "카카오 인가코드")})
    public ResponseEntity kakaoLogin(@RequestParam String code) {
        String accessToken = kakaoLoginService.getKakaoAccessToken(code);

        Map<String, Object> result = kakaoLoginService.getUserInfo(accessToken);
        Map<String, Object> kakaoAccount = (Map<String, Object>) result.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        User user = authService.findById(email);
//        log.info("user: {}", user.getId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
        }
        if (user.getSocial() == null) {
            authService.linkSocial(email, "kakao");
        }
        // JWT 생성
        JwtToken jwt = jwtGenerator.generateToken(user.getId());

        // JWT를 포함한 응답
        return ResponseEntity.ok(jwt);
    }
}