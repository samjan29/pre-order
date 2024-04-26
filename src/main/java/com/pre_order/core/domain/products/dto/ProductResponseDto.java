package com.pre_order.core.domain.products.dto;

public record ProductResponseDto(Long id, String name, String details, int price, int discount, int stock) {
}
