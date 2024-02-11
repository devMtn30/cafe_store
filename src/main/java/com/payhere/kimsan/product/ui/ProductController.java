package com.payhere.kimsan.product.ui;

import static com.payhere.kimsan.common.constant.DomainConstant.PAGE_DEFAULT_SIZE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.payhere.kimsan.common.CustomResponse;
import com.payhere.kimsan.common.login.LoginUser;
import com.payhere.kimsan.product.application.ProductService;
import com.payhere.kimsan.product.application.dto.AddProductRequest;
import com.payhere.kimsan.product.application.dto.GetProductListResponse;
import com.payhere.kimsan.product.application.dto.GetProductResponse;
import com.payhere.kimsan.product.application.dto.ProductResponse;
import com.payhere.kimsan.product.application.dto.UpdateProductRequest;
import com.payhere.kimsan.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CustomResponse<ProductResponse>> createProduct(@LoginUser CustomUserDetails userDetails,
        @Validated @RequestBody AddProductRequest request) {
        String userId = userDetails.getUsername();
        productService.saveProduct(request, userId);

        return ResponseEntity.status(CREATED)
                             .body(new CustomResponse<>(CREATED, productService.getProductResponse(userId)));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<CustomResponse<ProductResponse>> updateProduct(@LoginUser CustomUserDetails userDetails,
        @RequestBody UpdateProductRequest updateProductRequest, @PathVariable Long productId) {
        String userId = userDetails.getUsername();
        productService.updateProduct(updateProductRequest, userId, productId);

        return ResponseEntity.status(OK)
                             .body(new CustomResponse<>(OK, productService.getProductResponse(userId)));
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<CustomResponse<ProductResponse>> deleteProduct(@LoginUser CustomUserDetails userDetails,
        @PathVariable Long productId) {
        String userId = userDetails.getUsername();
        productService.deleteProduct(userId, productId);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<CustomResponse<GetProductListResponse>> showProducts(@LoginUser CustomUserDetails userDetails,
        @RequestParam Long cursor, @RequestParam int page) {
        PageRequest pageable = PageRequest.of(page, PAGE_DEFAULT_SIZE.getValue(), Sort.by("id").descending());
        var response = productService.findProductsByPage(userDetails.getUsername(), pageable, cursor);

        return new ResponseEntity<>(new CustomResponse<>(OK, response), OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CustomResponse<GetProductResponse>> showProduct(@LoginUser CustomUserDetails userDetails,
        @PathVariable Long productId) {
        String userId = userDetails.getUsername();
        GetProductResponse response = productService.findProduct(userId, productId);

        return new ResponseEntity<>(new CustomResponse<>(OK, response), OK);
    }
}
