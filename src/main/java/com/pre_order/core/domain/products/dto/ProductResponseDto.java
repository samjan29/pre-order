package com.pre_order.core.domain.products.dto;

import com.pre_order.core.domain.products.entity.Products;

public record ProductResponseDto(Long id, String name, String details, int price, int discount, int stock) {
    public ProductResponseDto(Products product) {
        this(product.getId(), product.getName(), product.getDetails(), product.getPrice(), product.getDiscount(), product.getStock());
    }
}
