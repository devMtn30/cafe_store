package com.payhere.kimsan.user.ui;

import com.payhere.kimsan.common.login.LoginUser;
import com.payhere.kimsan.user.application.AuthService;
import com.payhere.kimsan.user.application.dto.JwtAuthenticationResponse;
import com.payhere.kimsan.user.application.dto.SignInRequest;
import com.payhere.kimsan.user.application.dto.SignUpRequest;
import com.payhere.kimsan.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Validated @RequestBody SignUpRequest request) {
        authenticationService.createUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(
        @Validated @RequestBody SignInRequest request) {
        return ResponseEntity.ok().body(authenticationService.signin(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser CustomUserDetails user) {
        log.info("로그아웃");
        authenticationService.logout(user.getUsername());
        log.info("로그아웃 성공");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
