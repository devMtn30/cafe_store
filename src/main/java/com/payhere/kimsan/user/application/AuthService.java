package com.payhere.kimsan.user.application;

import static com.payhere.kimsan.common.config.EncryptConfig.encrypt;
import static com.payhere.kimsan.common.exception.ErrorCode.DUPLICATED_ID;
import static com.payhere.kimsan.common.exception.ErrorCode.INVALID_USER_INFO;

import com.payhere.kimsan.common.exception.CustomException;
import com.payhere.kimsan.user.application.dto.JwtAuthenticationResponse;
import com.payhere.kimsan.user.application.dto.SignInRequest;
import com.payhere.kimsan.user.application.dto.SignUpRequest;
import com.payhere.kimsan.user.domain.CustomUserDetails;
import com.payhere.kimsan.user.domain.User;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AesBytesEncryptor encryptor;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void createUser(SignUpRequest request) {
        validateUser(request);
        User user = getUser(request);
        userService.save(user);
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticateUser(request);
        User user = userService.findByUserId(request.userId())
                               .orElseThrow(() -> new CustomException(INVALID_USER_INFO));
        String jwt = jwtService.generateToken(new CustomUserDetails(user));
        saveToken(user.getUserId(), jwt);

        return JwtAuthenticationResponse.builder().accessToken(jwt).build();
    }

    public void logout(String userId) {
        redisTemplate.delete(userId);
    }

    private void validateUser(SignUpRequest request) {

        if (userService.findByUserId(request.userId()).isPresent()) {
            throw new CustomException(DUPLICATED_ID);
        }
    }

    private User getUser(SignUpRequest request) {
        return User.builder()
                   .userId(request.userId())
                   .password(passwordEncoder.encode(request.password()))
                   .name(request.name())
                   .regNo(encrypt(encryptor, request.regNo()))
                   .build();
    }

    private void saveToken(String userId, String jwt) {
        redisTemplate.opsForValue().set(userId, jwt, 60, TimeUnit.MINUTES);
    }

    private void authenticateUser(SignInRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.userId(),
                request.password()));
    }
}
