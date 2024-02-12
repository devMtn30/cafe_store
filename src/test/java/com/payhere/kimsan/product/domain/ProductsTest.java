package com.payhere.kimsan.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.payhere.kimsan.common.exception.CustomException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ProductsTest {

    @Test
    public void testAddProduct() {
        Products products = new Products();
        Product product = new Product("category", 100.0, 50.0, "name", "description", "barcode", LocalDate.now(), Size.LARGE);
        products.addProduct(product);
        assertThat(products.getSize()).isEqualTo(1);
    }

    @Test
    public void testUpdateProduct() {
        Products products = new Products();
        Product product = new Product("category", 100.0, 50.0, "name", "description", "barcode", LocalDate.now(), Size.LARGE);
        product.setId(1L);
        products.addProduct(product);
        Product updatedProduct = new Product("updatedCategory", 200.0, 100.0, "updatedName", "updatedDescription", "updatedBarcode", LocalDate.now(), Size.LARGE);
        updatedProduct.setId(product.getId());
        products.updateProduct(updatedProduct);
        Product lastProduct = products.getLastProduct();
        assertAll(
            () -> assertThat(lastProduct.getCategory()).isEqualTo("updatedCategory"),
            () -> assertThat(lastProduct.getPrice()).isEqualTo(200.0),
            () -> assertThat(lastProduct.getCost()).isEqualTo(100.0),
            () -> assertThat(lastProduct.getName()).isEqualTo("updatedName"),
            () -> assertThat(lastProduct.getDescription()).isEqualTo("updatedDescription"),
            () -> assertThat(lastProduct.getBarcode()).isEqualTo("updatedBarcode"),
            () -> assertThat(lastProduct.getExpirationDate()).isNotNull(),
            () -> assertThat(lastProduct.getSize()).isEqualTo(Size.LARGE)
        );
    }

    @Test
    public void testGetMatchProduct() {
        Products products = new Products();
        Product product = new Product("category", 100.0, 50.0, "name", "description", "barcode", LocalDate.now(), Size.LARGE);
        product.setId(1L);
        products.addProduct(product);
        Product matchProduct = products.getMatchProduct(product);
        assertThat(matchProduct).isEqualTo(product);
    }

    @Test
    public void testGetLastProduct() {
        Products products = new Products();
        assertThrows(CustomException.class, products::getLastProduct);
        Product product = new Product("category", 100.0, 50.0, "name", "description", "barcode", LocalDate.now(), Size.LARGE);
        products.addProduct(product);
        Product lastProduct = products.getLastProduct();
        assertThat(lastProduct).isEqualTo(product);
    }

    @Test
    public void testRemoveProduct() {
        Products products = new Products();
        Product product = new Product("category", 100.0, 50.0, "name", "description", "barcode", LocalDate.now(), Size.LARGE);
        product.setId(1L);
        products.addProduct(product);
        products.removeProduct(product);
        assertThat(products.getSize()).isEqualTo(0);
    }
}