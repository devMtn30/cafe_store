package com.payhere.kimsan.product.application.dto;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Size;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UpdateProductRequestTest {

    @Test
    public void toEntityTest() {
        // given
        Long id = 1L;
        String category = "testCategory";
        Double price = 100.0;
        Double cost = 50.0;
        String name = "testName";
        String description = "testDescription";
        String barcode = "testBarcode";
        LocalDate expirationDate = LocalDate.now();
        Size size = Size.SMALL;

        UpdateProductRequest request = new UpdateProductRequest(category, price, cost, name, description, barcode, expirationDate, size);

        // when
        Product product = request.toEntity(id);

        // then
        assertAll(
            () -> assertThat(product.getId()).isEqualTo(id),
            () -> assertThat(product.getCategory()).isEqualTo(category),
            () -> assertThat(product.getPrice()).isEqualTo(price),
            () -> assertThat(product.getCost()).isEqualTo(cost),
            () -> assertThat(product.getName()).isEqualTo(name),
            () -> assertThat(product.getDescription()).isEqualTo(description),
            () -> assertThat(product.getBarcode()).isEqualTo(barcode),
            () -> assertThat(product.getExpirationDate()).isEqualTo(expirationDate),
            () -> assertThat(product.getSize()).isEqualTo(size)
        );
    }
}
