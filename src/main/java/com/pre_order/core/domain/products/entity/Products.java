package com.pre_order.core.domain.products.entity;

import com.pre_order.core.domain.orders.entity.OrderItems;
import com.pre_order.core.domain.orders.entity.WishList;
import com.pre_order.core.global.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@Table(name = "products")
public class Products extends CommonEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "is_show")
    @ColumnDefault("true")
    private boolean isShow;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<DiscountLogs> discountLogs;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<OrderItems> orderItems;
}