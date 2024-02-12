package com.payhere.kimsan.product.domain;


import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductTest {

    @Test
    public void testProduct() {
        Product product = new Product("category", 100.0, 50.0, "name", "description", "barcode", LocalDate.now(), Size.LARGE);
        assertAll(
            () -> assertThat(product.getCategory()).isEqualTo("category"),
            () -> assertThat(product.getPrice()).isEqualTo(100.0),
            () -> assertThat(product.getCost()).isEqualTo(50.0),
            () -> assertThat(product.getName()).isEqualTo("name"),
            () -> assertThat(product.getDescription()).isEqualTo("description"),
            () -> assertThat(product.getBarcode()).isEqualTo("barcode"),
            () -> assertThat(product.getExpirationDate()).isNotNull(),
            () -> assertThat(product.getSize()).isEqualTo(Size.LARGE)
        );
    }

    @Test
    public void testUpdate() {
        Product product = new Product("category", 100.0, 50.0, "name", "description", "barcode", LocalDate.now(), Size.LARGE);
        Product updatedProduct = new Product("updatedCategory", 200.0, 100.0, "updatedName", "updatedDescription", "updatedBarcode", LocalDate.now(), Size.LARGE);
        product.update(updatedProduct);
        assertAll(
            () -> assertThat(product.getCategory()).isEqualTo("updatedCategory"),
            () -> assertThat(product.getPrice()).isEqualTo(200.0),
            () -> assertThat(product.getCost()).isEqualTo(100.0),
            () -> assertThat(product.getName()).isEqualTo("updatedName"),
            () -> assertThat(product.getDescription()).isEqualTo("updatedDescription"),
            () -> assertThat(product.getBarcode()).isEqualTo("updatedBarcode"),
            () -> assertThat(product.getExpirationDate()).isNotNull(),
            () -> assertThat(product.getSize()).isEqualTo(Size.LARGE)
        );
    }
}
