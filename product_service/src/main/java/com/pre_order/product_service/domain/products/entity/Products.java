package com.pre_order.product_service.domain.products.entity;

import com.pre_order.product_service.global.entity.CommonEntity;
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
    private Integer price;

    @Column(name = "discount", nullable = false)
    @ColumnDefault("0")
    private Integer discount;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "is_show")
    @ColumnDefault("true")
    private Boolean isShow;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<DiscountLogs> discountLogs;

}