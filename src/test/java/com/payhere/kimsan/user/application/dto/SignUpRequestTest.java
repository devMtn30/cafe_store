package com.payhere.kimsan.user.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpRequestTest {

    @Test
    public void testSignUpRequest() {
        String userId = "123-4567-8901";
        String password = "testPassword";
        String name = "testName";
        String regNo = "testRegNo";
        SignUpRequest request = new SignUpRequest(userId, password, name, regNo);

        assertAll("SignUpRequest",
            () -> assertEquals(userId, request.userId()),
            () -> assertEquals(password, request.password()),
            () -> assertEquals(name, request.name()),
            () -> assertEquals(regNo, request.regNo())
        );
    }
}
