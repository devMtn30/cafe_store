package com.payhere.kimsan.product.domain;

import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
}
