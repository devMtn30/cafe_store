package com.payhere.kimsan.user.application;

import com.payhere.kimsan.user.application.dto.SignInRequest;
import com.payhere.kimsan.user.application.dto.SignUpRequest;
import com.payhere.kimsan.user.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AesBytesEncryptor encryptor;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        SignUpRequest request = new SignUpRequest("123-4567-8901", "testPassword", "testName", "testRegNo");
        authService.createUser(request);

        verify(userService).save(any(User.class));
    }

    @Test
    public void testSignin() {
        SignInRequest request = new SignInRequest("123-4567-8901", "testPassword");
        User user = new User();
        when(userService.findByUserId(request.userId())).thenReturn(Optional.of(user));

        RedisTemplate<String, Object> mockRedisTemplate = mock(RedisTemplate.class);
        ValueOperations<String, Object> mockValueOps = mock(ValueOperations.class);
        when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOps);

        ReflectionTestUtils.setField(authService, "redisTemplate", mockRedisTemplate);

        assertDoesNotThrow(() -> authService.signin(request));
    }


    @Test
    public void testLogout() {
        String userId = "123-4567-8901";
        authService.logout(userId);

        verify(redisTemplate).delete(userId);
    }
}
