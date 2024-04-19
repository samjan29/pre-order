package com.pre_order.core.domain.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "discount_logs")
public class DiscountLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    @Column(name = "discount", nullable = false)
    private int discount;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

}