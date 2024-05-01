package com.pre_order.order_service.domain.orders.repository;

import com.pre_order.order_service.domain.orders.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
//    List<WishList> findAllByUsersIdAndIsDeleted(Long userId, Boolean isDeleted);
}
