package com.pre_order.core.domain.orders.service;

import com.pre_order.core.domain.orders.dto.WishListRequestDto;
import com.pre_order.core.domain.orders.entity.WishList;
import com.pre_order.core.domain.orders.repository.OrdersRepository;
import com.pre_order.core.domain.orders.repository.WishListRepository;
import com.pre_order.core.domain.products.entity.Products;
import com.pre_order.core.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final WishListRepository wishListRepository;

    public void addToWishList(WishListRequestDto wishListRequestDto, Users user, Products product) {
        wishListRepository.save(WishList.builder()
                .products(product)
                .users(user)
                .quantity(wishListRequestDto.quantity())
                .build());
    }
}
