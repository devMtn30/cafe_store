package com.payhere.kimsan.user.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtAuthenticationResponseTest {

    @Test
    public void testAccessToken() {
        String token = "myAccessToken";
        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                                                                      .accessToken(token)
                                                                      .build();

        assertEquals(JwtAuthenticationResponse.PRE_FIX + token, response.accessToken());
    }
}