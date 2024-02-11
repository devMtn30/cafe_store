package com.payhere.kimsan.product.application.dto;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Size;
import java.time.LocalDate;

public record GetProductResponse(
    Long id,
    String category,
    Double price,
    Double cost,
    String name,
    String description,
    String barcode,
    LocalDate expirationDate,
    Size size
) {

    public static GetProductResponse from(Product product) {
        return new GetProductResponse(product.getId(),
            product.getCategory(),
            product.getPrice(),
            product.getCost(),
            product.getName(),
            product.getDescription(),
            product.getBarcode(),
            product.getExpirationDate(),
            product.getSize());
    }

}
