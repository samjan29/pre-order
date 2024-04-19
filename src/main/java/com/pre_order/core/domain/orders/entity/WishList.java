package com.pre_order.core.domain.orders.entity;

import com.pre_order.core.domain.products.entity.Products;
import com.pre_order.core.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "wish_list")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @CreationTimestamp
    @Column(name = "added_at")
    private Timestamp addedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}