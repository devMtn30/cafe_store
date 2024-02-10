package com.payhere.kimsan.acceptance.product;

import static com.payhere.kimsan.acceptance.AcceptanceTestBase.assertStatusCode;
import static com.payhere.kimsan.acceptance.login.LoginAcceptanceTestSource.사전_회원가입;
import static com.payhere.kimsan.acceptance.logout.LogoutAcceptanceTestSource.사전_로그인;
import static com.payhere.kimsan.acceptance.product.ProductAcceptanceTestSource.*;
import static com.payhere.kimsan.acceptance.product.ProductAcceptanceTestSource.상품등록_요청;

import com.payhere.kimsan.acceptance.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

}