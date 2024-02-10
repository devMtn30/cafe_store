package com.payhere.kimsan.product.ui;

import static org.springframework.http.HttpStatus.CREATED;

import com.payhere.kimsan.common.login.LoginUser;
import com.payhere.kimsan.product.application.ProductService;
import com.payhere.kimsan.product.application.dto.AddProductRequest;
import com.payhere.kimsan.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@LoginUser CustomUserDetails userDetails,
        @Validated @RequestBody AddProductRequest request) {
        productService.saveProduct(request, userDetails.getUsername());
        return ResponseEntity.status(CREATED).build();
    }
}
