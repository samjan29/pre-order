package com.pre_order.order_service.domain.orders.dto;

import com.pre_order.order_service.domain.orders.entity.WishList;

import java.util.List;

public record WishListResponseDto(Long wishListId, Long productId, String name, int price, int quantity) {
    public static List<WishListResponseDto> listFrom(List<WishList> wishListList) {
//        return wishListList.stream()
//                .map(wishList -> {
//                    Products products = wishList.getProducts();
//                    return new WishListResponseDto(
//                            wishList.getId(),
//                            products.getId(),
//                            products.getName(),
//                            products.getPrice(),
//                            wishList.getQuantity());
//                })
//                .toList();
        return null;
    }
}
