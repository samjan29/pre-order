package com.pre_order.product_service.domain.products.controller;

import com.pre_order.product_service.domain.products.dto.ProductResponseDto;
import com.pre_order.product_service.domain.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProductList(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok(productsService.getProductList(page));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable(name = "productId") Long productId) {
        return ResponseEntity.ok(productsService.getProduct(productId));
    }

}
