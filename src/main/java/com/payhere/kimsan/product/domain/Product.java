package com.payhere.kimsan.product.domain;

import com.payhere.kimsan.user.domain.User;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private Double price;

    private Double cost;

    private String name;

    private String description;

    private String barcode;

    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private Size size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Product(String category, Double price, Double cost, String name, String description,
        String barcode, LocalDate expirationDate, Size size) {
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.size = size;
    }


    public void update(Product product) {
        updateIfNotNull(product::getCategory, (p, v) -> this.setCategory(v));
        updateIfNotNull(product::getPrice, (p, v) -> this.setPrice(v));
        updateIfNotNull(product::getCost, (p, v) -> this.setCost(v));
        updateIfNotNull(product::getName, (p, v) -> this.setName(v));
        updateIfNotNull(product::getDescription, (p, v) -> this.setDescription(v));
        updateIfNotNull(product::getBarcode, (p, v) -> this.setBarcode(v));
        updateIfNotNull(product::getExpirationDate, (p, v) -> this.setExpirationDate(v));
        updateIfNotNull(product::getSize, (p, v) -> this.setSize(v));
    }

    private <T> void updateIfNotNull(Supplier<T> getter, BiConsumer<Product, T> setter) {
        T value = getter.get();
        if (value != null) {
            setter.accept(this, value);
        }
    }
}
