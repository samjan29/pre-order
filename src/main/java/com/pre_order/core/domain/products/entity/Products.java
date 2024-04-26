package com.pre_order.core.domain.products.entity;

import com.pre_order.core.domain.orders.entity.OrderItems;
import com.pre_order.core.domain.orders.entity.WishList;
import com.pre_order.core.global.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
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
    private Boolean isShow;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<DiscountLogs> discountLogs;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<OrderItems> orderItems;

    public void updateStock(int stock) {
        this.stock = stock;
    }
}