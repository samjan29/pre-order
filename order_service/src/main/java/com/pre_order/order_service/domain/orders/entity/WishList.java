package com.pre_order.order_service.domain.orders.entity;

import com.pre_order.order_service.global.exception.CustomException;
import com.pre_order.order_service.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "wish_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @CreationTimestamp
    @Column(name = "added_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime addedAt;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted;

    public void updateQuantity(int quantity) {
        if (quantity < 1) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        this.quantity = quantity;
    }
}