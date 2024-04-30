package com.pre_order.core.domain.orders.controller;

import com.pre_order.core.domain.orders.dto.QuantityDto;
import com.pre_order.core.domain.orders.service.OrdersService;
import com.pre_order.core.domain.products.entity.Products;
import com.pre_order.core.domain.products.service.ProductsService;
import com.pre_order.core.domain.users.entity.Users;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final ProductsService productsService;
    private final OrdersService ordersService;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> createOrder(
            @PathVariable(name = "productId") Long productId,
            @Valid @RequestBody QuantityDto quantityDto,
            @AuthenticationPrincipal Users user) {
        Products product = productsService.checkProduct(productId);
        ordersService.createOrder(product, quantityDto, user);
        productsService.updateStock(quantityDto.quantity(), product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
