package com.pre_order.core.domain.orders.repository;

import com.pre_order.core.domain.orders.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
