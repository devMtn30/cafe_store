package com.payhere.kimsan.user.application;

import com.payhere.kimsan.user.application.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000L);
        ReflectionTestUtils.setField(jwtService, "jwtSecretKey", "aslkdaslkdnas89d7as8d7sa8ds8zd678zsd8sz567ds7zd4sa8as");  // 임의의 비밀키

        RedisTemplate<String, Object> mockRedisTemplate = mock(RedisTemplate.class);
        ValueOperations<String, Object> mockValueOps = mock(ValueOperations.class);
        when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOps);
        when(mockValueOps.get(anyString())).thenReturn("someToken");

        ReflectionTestUtils.setField(jwtService, "redisTemplate", mockRedisTemplate);
    }


    @Test
    public void testGenerateToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
    }

    @Test
    public void testIsTokenValid() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    public void testIsUserTokenExpired() {
        when(userDetails.getUsername()).thenReturn("testUser");
        boolean isExpired = jwtService.isUserTokenExpired(userDetails);

        assertFalse(isExpired);
    }
}
