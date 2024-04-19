package com.pre_order.core.domain.orders.entity;

import com.pre_order.core.domain.orders.status.OrderStatus;
import com.pre_order.core.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    @Column(name = "order_date")
    private Timestamp orderDate;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<OrderItems> orderItems;
}