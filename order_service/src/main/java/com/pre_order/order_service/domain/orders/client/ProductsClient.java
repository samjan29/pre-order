package com.pre_order.order_service.domain.orders.client;

import com.pre_order.order_service.domain.orders.dto.ProductInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "productsService", url = "http://localhost:8082/api/internal/products")
public interface ProductsClient {

    @GetMapping("/check-product/{productId}")
    ResponseEntity<ProductInfoResponseDto> checkProduct(@PathVariable(name = "productId") Long productId);

    @PatchMapping("/{productId}/stock")
    ResponseEntity<Void> updateStock(@PathVariable(name = "productId") Long productId, @RequestBody Integer quantity);

}
