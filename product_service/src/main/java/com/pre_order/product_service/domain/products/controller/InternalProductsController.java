package com.pre_order.product_service.domain.products.controller;

import com.pre_order.product_service.domain.products.dto.ProductInfoResponseDto;
import com.pre_order.product_service.domain.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/products")
@RequiredArgsConstructor
public class InternalProductsController {

    private final ProductsService productsService;

    @GetMapping("/check-product/{productId}")
    ResponseEntity<ProductInfoResponseDto> checkProduct(@PathVariable(name = "productId") Long productId) {
        return ResponseEntity.ok(productsService.checkProduct(productId));
    }

    @PatchMapping("/{productId}/stock")
    ResponseEntity<Void> updateStock(@PathVariable(name = "productId") Long productId, @RequestBody Integer quantity) {
        productsService.updateStock(productId, quantity);
        return ResponseEntity.noContent().build();
    }
}
