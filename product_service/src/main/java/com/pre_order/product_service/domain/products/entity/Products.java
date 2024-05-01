package com.pre_order.product_service.domain.products.entity;

import com.pre_order.product_service.global.entity.CommonEntity;
import com.pre_order.product_service.global.exception.CustomException;
import com.pre_order.product_service.global.exception.ErrorCode;
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
    private List<DiscountLogs> discountLogs;

    public void updateStock(int quantity) {
        if (this.stock - quantity < 0) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        this.stock -= quantity;
    }
}