package com.payhere.kimsan.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ResponseParser {

    public static Long getIdFromResponse(final ExtractableResponse<Response> response) {
        return response.jsonPath().getLong("id");
    }

    public static String getTokenFromResponse(final ExtractableResponse<Response> response) {
        return response.jsonPath().getString("accessToken");
    }


}
