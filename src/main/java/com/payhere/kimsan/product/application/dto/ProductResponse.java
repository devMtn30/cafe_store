package com.payhere.kimsan.product.application.dto;

public record ProductResponse(Long id) {

    public ProductResponse(Long id) {
        this.id = id;
    }
}
