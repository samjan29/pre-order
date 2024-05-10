package com.pre_order.order_service.domain.orders.controller;

import com.pre_order.order_service.domain.orders.dto.QuantityDto;
import com.pre_order.order_service.domain.orders.dto.WishListRequestDto;
import com.pre_order.order_service.domain.orders.dto.WishListResponseDto;
import com.pre_order.order_service.domain.orders.service.WishListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/wish-list")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @PostMapping
    public ResponseEntity<Void> addToWishList(@Valid @RequestBody WishListRequestDto wishListRequestDto /*, @AuthenticationPrincipal Users user*/) {
        // Products product = productsService.checkProduct(wishListRequestDto.productId());
        wishListService.addToWishList(wishListRequestDto/*, user, product*/);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<WishListResponseDto>> getWishList(/*@AuthenticationPrincipal Users user*/) {
        return ResponseEntity.ok().body(wishListService.getWishList(/*user*/));
    }

    @PatchMapping("/{wishListId}")
    public ResponseEntity<QuantityDto> updateWishList(@PathVariable(name = "wishListId") Long wishListId, @Valid @RequestBody WishListRequestDto wishListRequestDto) {
        return ResponseEntity.ok().body(wishListService.updateWishList(wishListId, wishListRequestDto));
    }

}
