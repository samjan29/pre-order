package com.pre_order.order_service.domain.orders.repository;

import com.pre_order.order_service.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
