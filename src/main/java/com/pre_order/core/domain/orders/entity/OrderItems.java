package com.pre_order.core.domain.orders.entity;

import com.pre_order.core.domain.products.entity.Products;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @Column(name = "purchase_price", nullable = false)
    private int purchasePrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

}