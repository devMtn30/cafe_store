package com.payhere.kimsan.product.application.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Size;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AddProductRequestTest {

    @Test
    public void toEntityTest() {
        // given
        String category = "testCategory";
        Double price = 100.0;
        Double cost = 50.0;
        String name = "testName";
        String description = "testDescription";
        String barcode = "testBarcode";
        LocalDate expirationDate = LocalDate.now();
        Size size = Size.SMALL;

        AddProductRequest request = new AddProductRequest(category, price, cost, name, description, barcode, expirationDate, size);

        // when
        Product product = request.toEntity();

        // then
        assertAll(
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
