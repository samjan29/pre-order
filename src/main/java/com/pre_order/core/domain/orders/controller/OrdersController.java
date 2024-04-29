package com.pre_order.core.domain.orders.controller;

import com.pre_order.core.domain.orders.dto.QuantityDto;
import com.pre_order.core.domain.orders.dto.WishListRequestDto;
import com.pre_order.core.domain.orders.dto.WishListResponseDto;
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

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final ProductsService productsService;
    private final OrdersService ordersService;

    @PostMapping("/wish-list")
    public ResponseEntity<Void> addToWishList(@Valid @RequestBody WishListRequestDto wishListRequestDto, @AuthenticationPrincipal Users user) {
        Products product = productsService.checkProduct(wishListRequestDto);
        ordersService.addToWishList(wishListRequestDto, user, product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/wish-list")
    public ResponseEntity<List<WishListResponseDto>> getWishList(@AuthenticationPrincipal Users user) {
        return ResponseEntity.ok().body(ordersService.getWishList(user));
    }

    @PatchMapping("/wish-list/{wishListId}")
    public ResponseEntity<QuantityDto> updateWishList(@PathVariable(name = "wishListId") Long wishListId, @Valid @RequestBody WishListRequestDto wishListRequestDto) {
        return ResponseEntity.ok().body(ordersService.updateWishList(wishListId, wishListRequestDto));
    }

}
