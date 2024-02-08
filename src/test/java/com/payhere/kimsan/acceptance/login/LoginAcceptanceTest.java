package com.payhere.kimsan.acceptance.login;

import static com.payhere.kimsan.acceptance.AcceptanceTestBase.assertStatusCode;
import static com.payhere.kimsan.acceptance.ResponseParser.getTokenFromResponse;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.로그인_계정_생성;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.로그인_요청;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.사전_회원가입;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.payhere.kimsan.acceptance.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

@DisplayName("로그인 테스트")
@AcceptanceTest
class LoginAcceptanceTest {

    @BeforeEach
    void setUp() {
        사전_회원가입();
    }

    @DisplayName("아이디가 일치하지 않으면 로그인에 실패한다.")
    @ParameterizedTest
    @CsvSource(value = {
        "szs,123",
        "hello,world",
        ","
    })
    void fail_login_id(String userId, String password) {
        // given
        var loginParams = 로그인_계정_생성(userId, password);

        // when
        var response = 로그인_요청(loginParams);

        // then
        assertStatusCode(response, HttpStatus.BAD_REQUEST);
    }

    @DisplayName("비밀번호가 일치하지 않으면 로그인에 실패한다.")
    @ParameterizedTest
    @CsvSource(value = {
        "gildong,hi",
        "gildong,hello",
        "gildong,kkkk",
    })
    void fail_login_password(String userId, String password) {
        // given
        var loginParams = 로그인_계정_생성(userId, password);

        // when
        var response = 로그인_요청(loginParams);

        // then
        assertStatusCode(response, HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("로그인에 성공한다.")
    void success_login() {
        final String id = "gildong";
        final String password = "123";
        // given
        var loginParams = 로그인_계정_생성(id, password);

        // when
        var response = 로그인_요청(loginParams);

        // then
        assertStatusCode(response, HttpStatus.OK);
        assertThat(getTokenFromResponse(response)).startsWithIgnoringCase(
            "Bearer ");
    }

}