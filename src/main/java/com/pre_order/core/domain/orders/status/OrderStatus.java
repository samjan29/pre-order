package com.pre_order.core.domain.orders.status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public enum OrderStatus {
    ORDERED("주문 완료"),
    CONFIRMED("배송 중"),
    CANCELED("주문 취소"),
    DELIVERED("배송 완료"),
    RETURNED("반품");

    private String status;
}
