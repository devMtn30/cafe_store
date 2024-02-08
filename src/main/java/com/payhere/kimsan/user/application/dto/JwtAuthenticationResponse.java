package com.payhere.kimsan.user.application.dto;

import lombok.Builder;

@Builder
public record JwtAuthenticationResponse(String accessToken) {

    public static final String PRE_FIX = "Bearer ";

    @Override
    public String accessToken() {
        return PRE_FIX + accessToken;
    }
}
