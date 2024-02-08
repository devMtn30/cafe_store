package com.payhere.kimsan.acceptance.signup;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;

public class SignUpAcceptanceTestSource {

    public static HashMap<String, String> createUserData(String name, String regNo, String userId) {
        HashMap<String, String> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("name", name);
        userData.put("password", "123");
        userData.put("regNo", regNo);
        return userData;
    }

    public static ExtractableResponse<Response> 회원가입(
        HashMap<String, String> userData) {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .body(userData)
            .contentType(ContentType.JSON)
            .when().post("/signup")
            .then().log().all().extract();
        return response;
    }

}
