package com.payhere.kimsan.acceptance.logout;

import static com.payhere.kimsan.acceptance.ResponseParser.getTokenFromResponse;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.로그인_계정_생성;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.로그인_요청;

import com.payhere.kimsan.acceptance.signup.SignUpAcceptanceTestSource;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;

public class LogoutAcceptanceTestSource {

    public static String 사전_로그인() {
        final String id = "010-9352-2209";
        final String password = "123";
        // given
        var loginParams = 로그인_계정_생성(id, password);

        // when
        return getTokenFromResponse(로그인_요청(loginParams));
    }

    public static ExtractableResponse<Response> 로그아웃_요청(String token) {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", token)
            .contentType(ContentType.JSON)
            .when().post("/auth/logout")
            .then().log().all().extract();
        return response;
    }


    public static ExtractableResponse<Response> 테스트_요청(String token) {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", token)
            .contentType(ContentType.JSON)
            .when().get("/health")
            .then().log().all().extract();
        return response;
    }
}
