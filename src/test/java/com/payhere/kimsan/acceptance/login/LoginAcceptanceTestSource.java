package com.payhere.kimsan.acceptance.login;

import static com.payhere.kimsan.acceptance.signup.SignUpAcceptanceTestSource.*;

import com.payhere.kimsan.acceptance.signup.SignUpAcceptanceTestSource;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;

public class LoginAcceptanceTestSource {

    public static ExtractableResponse<Response> 로그인_요청(
        HashMap<String, String> userData) {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .body(userData)
            .contentType(ContentType.JSON)
            .when().post("/auth/login")
            .then().log().all().extract();
        return response;
    }

    public static ExtractableResponse<Response> 사전_회원가입() {
        final String name = "홍길동";
        final String regNo = "860824-1655068";
        final String userId = "010-9352-2209";

        HashMap<String, String> userData = createUserData(name, regNo,
            userId);
        ExtractableResponse<Response> response = 회원가입(userData);

        return response;
    }

    public static HashMap<String, String> 로그인_계정_생성(String id, String password) {
        HashMap<String, String> loginParams = new HashMap<>();
        loginParams.put("userId", id);
        loginParams.put("password", password);
        return loginParams;
    }
}
