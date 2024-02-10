package com.payhere.kimsan.acceptance.product;

import com.payhere.kimsan.product.application.dto.AddProductRequest;
import com.payhere.kimsan.product.application.dto.UpdateProductRequest;
import com.payhere.kimsan.product.domain.Size;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;

public class ProductAcceptanceTestSource {


    public static ExtractableResponse<Response> 상품등록_요청(String token, AddProductRequest request) {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", token)
            .body(request)
            .contentType(ContentType.JSON)
            .when().post("/product")
            .then().log().all().extract();
        return response;
    }

    public static AddProductRequest 상품생성() {
        return new AddProductRequest(
            "Electronics",
            1500.00,
            1000.00,
            "Smartphone",
            "High-end smartphone with latest features",
            "1234567890123",
            LocalDate.of(2024, 12, 31),
            Size.LARGE
        );
    }

    public static ExtractableResponse<Response> 상품수정_요청(String token, UpdateProductRequest request,
        Long productId) {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", token)
            .body(request)
            .contentType(ContentType.JSON)
            .when().patch("/product/" + productId)
            .then().log().all().extract();
        return response;
    }

    public static UpdateProductRequest 상품수정생성() {
        return new UpdateProductRequest(
            null,
            null,
            null,
            "iPhone15",
            "Update smartPhone",
            "9876554213",
            null,
            null
        );

    }
}
