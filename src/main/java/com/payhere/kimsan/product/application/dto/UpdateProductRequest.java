package com.payhere.kimsan.product.application.dto;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Size;
import java.time.LocalDate;

public record UpdateProductRequest(
    String category,
    Double price,
    Double cost,
    String name,
    String description,
    String barcode,
    LocalDate expirationDate,
    Size size
) {

    public Product toEntity(Long productId) {
        return Product.builder().
                id(productId).
                category(category).
                price(price).
                cost(cost).
                name(name).
                description(description).
                barcode(barcode).
                expirationDate(expirationDate).
                size(size).build();
    }
}
