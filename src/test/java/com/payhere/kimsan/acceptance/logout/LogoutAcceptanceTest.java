package com.payhere.kimsan.acceptance.logout;

import static com.payhere.kimsan.acceptance.AcceptanceTestBase.assertStatusCode;
import static com.payhere.kimsan.acceptance.ResponseParser.getTokenFromResponse;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.로그인_계정_생성;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.로그인_요청;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.사전_회원가입;
import static com.payhere.kimsan.acceptance.logout.LogoutAcceptanceTestSource.로그아웃_요청;
import static com.payhere.kimsan.acceptance.logout.LogoutAcceptanceTestSource.사전_로그인;
import static com.payhere.kimsan.acceptance.logout.LogoutAcceptanceTestSource.테스트_요청;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.payhere.kimsan.acceptance.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

@DisplayName("로그아웃 테스트")
@AcceptanceTest
class LogoutAcceptanceTest {

    private String token;

    @BeforeEach
    void setUp() {
        사전_회원가입();
        token = 사전_로그인();
    }

    @Test
    @DisplayName("로그아웃시 토큰이 삭제되어 다른 API를 사용할 수 없다.")
    void success_logout() {
        // given
        var logoutResponse = 로그아웃_요청(token);

        // when
        var response = 테스트_요청(token);

        // then
        assertStatusCode(logoutResponse, HttpStatus.OK);
        assertStatusCode(response, HttpStatus.FORBIDDEN);
    }

}