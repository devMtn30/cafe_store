package com.payhere.kimsan.acceptance.product;

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
    @NotBlank Size size
) {

}
