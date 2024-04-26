package com.pre_order.core.domain.products.controller;

import com.pre_order.core.domain.products.dto.ProductResponseDto;
import com.pre_order.core.domain.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
