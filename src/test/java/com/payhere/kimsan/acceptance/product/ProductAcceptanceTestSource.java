package com.payhere.kimsan.acceptance.product;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ProductAcceptanceTestSource {


    public static ExtractableResponse<Response> 상품등록_요청(String token, AddProductRequest request) {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", token)
            .contentType(ContentType.JSON)
            .when().post("/product")
            .then().log().all().extract();
        return response;
    }
}
