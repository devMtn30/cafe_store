package com.payhere.kimsan.user.ui;

import com.payhere.kimsan.user.application.AuthService;
import com.payhere.kimsan.user.application.dto.JwtAuthenticationResponse;
import com.payhere.kimsan.user.application.dto.SignInRequest;
import com.payhere.kimsan.user.application.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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
}