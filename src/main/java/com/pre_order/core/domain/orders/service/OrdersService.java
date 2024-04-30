package com.pre_order.core.domain.orders.service;

import com.pre_order.core.domain.orders.dto.QuantityDto;
import com.pre_order.core.domain.orders.entity.Orders;
import com.pre_order.core.domain.orders.repository.OrdersRepository;
import com.pre_order.core.domain.orders.status.OrderStatus;
import com.pre_order.core.domain.products.entity.Products;
import com.pre_order.core.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public void createOrder(Products product, QuantityDto quantityDto, Users user) {
        Orders.builder()
                .users(user)
                .totalPrice(product.getPrice() * quantityDto.quantity())
                .status(OrderStatus.ORDERED)
                .build();
    }
}
