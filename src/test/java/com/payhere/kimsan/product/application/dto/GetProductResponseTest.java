package com.payhere.kimsan.product.application.dto;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Size;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GetProductResponseTest {

    @Test
    public void fromTest() {
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

        Product product = new Product(category, price, cost, name, description, barcode, expirationDate, size);
        product.setId(id);

        // when
        GetProductResponse response = GetProductResponse.from(product);

        // then
        assertAll(
            () -> assertThat(response.id()).isEqualTo(id),
            () -> assertThat(response.category()).isEqualTo(category),
            () -> assertThat(response.price()).isEqualTo(price),
            () -> assertThat(response.cost()).isEqualTo(cost),
            () -> assertThat(response.name()).isEqualTo(name),
            () -> assertThat(response.description()).isEqualTo(description),
            () -> assertThat(response.barcode()).isEqualTo(barcode),
            () -> assertThat(response.expirationDate()).isEqualTo(expirationDate),
            () -> assertThat(response.size()).isEqualTo(size)
        );
    }
}
