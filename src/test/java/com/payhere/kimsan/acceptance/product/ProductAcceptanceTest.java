package com.payhere.kimsan.acceptance.product;

import static com.payhere.kimsan.acceptance.AcceptanceTestBase.assertStatusCode;
import static com.payhere.kimsan.acceptance.ResponseParser.getIdFromResponse;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.사전_회원가입;
import static com.payhere.kimsan.acceptance.logout.LogoutAcceptanceTestSource.사전_로그인;
import static com.payhere.kimsan.acceptance.product.ProductAcceptanceTestSource.*;
import static com.payhere.kimsan.acceptance.product.ProductAcceptanceTestSource.상품등록_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.payhere.kimsan.acceptance.AcceptanceTest;
import com.payhere.kimsan.product.application.dto.GetProductListResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

@DisplayName("상품 관련 테스트")
@AcceptanceTest
class ProductAcceptanceTest {

    private String token;

    @BeforeEach
    void setUp() {
        사전_회원가입();
        token = 사전_로그인();
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void success_product_add() {
        // given
        var request = 상품생성();

        // when
        var response = 상품등록_요청(token, request);

        // then
        assertStatusCode(response, HttpStatus.CREATED);
    }

    @Test
    @DisplayName("상품 부분수정 테스트")
    void success_product_update() {
        // given
        var createResponse = 상품등록_요청(token, 상품생성());
        final Long productId = getIdFromResponse(createResponse);
        var request = 상품수정생성();

        // when
        var response = 상품수정_요청(token, request, productId);

        // then
        assertStatusCode(response, HttpStatus.OK);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void success_product_delete() {
        // given
        var createResponse = 상품등록_요청(token, 상품생성());
        final Long productId = getIdFromResponse(createResponse);

        // when
        var response = 상품삭제_요청(token, productId);

        // then
        assertStatusCode(response, HttpStatus.NO_CONTENT);
    }
    @Test
    @DisplayName("상품 목록조회 테스트")
    void success_products_find() {
        // given
        Stream.iterate(0, i -> i + 1)
              .limit(20)
              .forEach(i -> 상품등록_요청(token, 상품생성("테스트" + i)));
        var lastResponse = 상품등록_요청(token, 상품생성());
        Long lastAddedProductId = getIdFromResponse(lastResponse);

        // when
        final int page = 0;
        var response = 상품목록조회_요청(token, 상품목록_파라미터생성(lastAddedProductId, page));

        // then
        assertStatusCode(response, HttpStatus.OK);
        assertThat(응답데이터크기(response)).isEqualTo(10);
        assertThat(응답데이터_다음여부(response)).isEqualTo(true);
    }

    @Test
    @DisplayName("상품 조회 테스트")
    void success_product_find() {
        // given
        var createResponse = 상품등록_요청(token, 상품생성());
        Long productId = getIdFromResponse(createResponse);

        // when
        final int page = 0;
        var response = 상품조회_요청(token, productId);
        Long responseId = getIdFromResponse(response);

        // then
        assertStatusCode(response, HttpStatus.OK);
        assertThat(productId).isEqualTo(responseId);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "슈크림",
        "크림",
        "라떼",
        "ㄹㄸ",
        "ㅅㅋㄹ"
    })
    @DisplayName("상품 이름 기반 검색 테스트")
    void success_product_searchByName(final String 검색어) {
        // given
        final String 상품명 = "슈크림 라떼";
        var createResponse = 상품등록_요청(token, 상품생성(상품명));
        final Long productId = getIdFromResponse(createResponse);

        // when
        var response = 상품이름기반검색_요청(token, 검색어);
        final String 응답_상품명 = 응답상품명_찾기(response);

        // then
        assertStatusCode(response, HttpStatus.OK);
        assertThat(응답_상품명).isEqualTo(상품명);
    }

    private static String 응답상품명_찾기(ExtractableResponse<Response> response) {
        return response.jsonPath().getString("data[0].name");
    }

    private int 응답데이터크기(ExtractableResponse<Response> response) {
        return response.jsonPath().getObject("data", GetProductListResponse.class).products().size();
    }
    private boolean 응답데이터_다음여부(ExtractableResponse<Response> response) {
        return response.jsonPath().getObject("data", GetProductListResponse.class).hasNext();
    }

}