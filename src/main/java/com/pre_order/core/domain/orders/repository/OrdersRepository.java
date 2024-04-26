package com.pre_order.core.domain.orders.repository;

import com.pre_order.core.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
