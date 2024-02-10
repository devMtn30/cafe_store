package com.payhere.kimsan.product.application.dto;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    public Product toEntity() {
        return Product.builder().
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
