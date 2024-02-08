package com.payhere.kimsan.acceptance.signup;


import static com.payhere.kimsan.acceptance.AcceptanceTestBase.assertStatusCode;
import static com.payhere.kimsan.acceptance.signup.SignUpAcceptanceTestSource.*;

import com.payhere.kimsan.acceptance.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

@DisplayName("회원가입 테스트")
@AcceptanceTest
class SignUpAcceptanceTest {

    @DisplayName("중복된 아이디가 있는 경우 회원 가입에 실패한다.")
    @Test
    void fail_signup_duplicated_userId() {
        // given
        final String name = "홍길동";
        final String regNo = "860824-1655068";
        final String userId = "gildong";
        var userData = createUserData(name, regNo,
            userId);

        // when
        회원가입(userData);
        var response = 회원가입(userData);

        // then
        assertStatusCode(response, HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "홍길동,860824-1655068,u1",
        "김둘리,921108-1582816,u2",
        "마징가,880601-2455116,u3",
        "배지터,910411-1656116,u4",
        "손오공,820326-2715702,u5",
    })
    @DisplayName("회원 가입에 성공한다.")
    void success_signup(String name, String regNo, String userId) {
        // given
        var userData = createUserData(name, regNo,
            userId);

        // when
        var response = 회원가입(userData);

        // then
        assertStatusCode(response, HttpStatus.CREATED);
    }

}