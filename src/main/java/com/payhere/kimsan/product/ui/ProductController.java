package com.payhere.kimsan.product.ui;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.payhere.kimsan.common.login.LoginUser;
import com.payhere.kimsan.product.application.ProductService;
import com.payhere.kimsan.product.application.dto.AddProductRequest;
import com.payhere.kimsan.product.application.dto.ProductResponse;
import com.payhere.kimsan.product.application.dto.UpdateProductRequest;
import com.payhere.kimsan.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<ProductResponse> createProduct(@LoginUser CustomUserDetails userDetails,
        @Validated @RequestBody AddProductRequest request) {
        String userId = userDetails.getUsername();
        productService.saveProduct(request, userId);
        return ResponseEntity.status(CREATED)
                             .body(productService.getProductResponse(userId));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@LoginUser CustomUserDetails userDetails,
        @RequestBody UpdateProductRequest updateProductRequest, @PathVariable Long productId) {
        String userId = userDetails.getUsername();
        productService.updateProduct(updateProductRequest, userId, productId);

        return ResponseEntity.status(OK)
                             .body(productService.getProductResponse(userId));
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@LoginUser CustomUserDetails userDetails,
        @PathVariable Long productId) {
        String userId = userDetails.getUsername();
        productService.deleteProduct(userId, productId);

        return ResponseEntity.status(NO_CONTENT).build();
    }
}
