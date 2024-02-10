package com.payhere.kimsan.product.application.dto;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AddProductRequest(
    @NotBlank String category,
    @NotNull Double price,
    @NotNull Double cost,
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank String barcode,
    @NotNull LocalDate expirationDate,
    @NotNull Size size
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
