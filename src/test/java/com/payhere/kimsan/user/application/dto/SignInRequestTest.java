package com.payhere.kimsan.user.application.dto;

import com.payhere.kimsan.user.application.dto.SignInRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignInRequestTest {

    @Test
    public void testSignInRequest() {
        String userId = "testUser";
        String password = "testPassword";
        SignInRequest request = new SignInRequest(userId, password);

        assertEquals(userId, request.userId());
        assertEquals(password, request.password());
    }
}
