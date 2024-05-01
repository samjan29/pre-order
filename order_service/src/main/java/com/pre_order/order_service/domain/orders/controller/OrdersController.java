package com.pre_order.order_service.domain.orders.controller;

import com.pre_order.order_service.domain.orders.dto.QuantityDto;
import com.pre_order.order_service.domain.orders.service.OrdersService;
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

    private final OrdersService ordersService;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> createOrder(
            @PathVariable(name = "productId") Long productId,
            @Valid @RequestBody QuantityDto quantityDto
            /*@AuthenticationPrincipal Users user*/) {
        // Products product = productsService.checkProduct(productId);
        // ordersService.createOrder(product, quantityDto, user);
        // productsService.updateStock(quantityDto.quantity(), product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
