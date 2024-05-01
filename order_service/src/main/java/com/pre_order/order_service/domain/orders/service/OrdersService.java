package com.pre_order.order_service.domain.orders.service;

import com.pre_order.order_service.domain.orders.dto.QuantityDto;
import com.pre_order.order_service.domain.orders.entity.Orders;
import com.pre_order.order_service.domain.orders.repository.OrdersRepository;
import com.pre_order.order_service.domain.orders.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

//    public void createOrder(Products product, QuantityDto quantityDto, Users user) {
//        Orders.builder()
//                .users(user)
//                .totalPrice(product.getPrice() * quantityDto.quantity())
//                .status(OrderStatus.ORDERED)
//                .build();
//    }
}
