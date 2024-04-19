package com.pre_order.core.domain.users.entity;

import com.pre_order.core.domain.orders.entity.Orders;
import com.pre_order.core.domain.orders.entity.WishList;
import com.pre_order.core.global.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class Users extends CommonEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "address1", nullable = false)
    private String address1;    // 시도군구

    @Column(name = "address2", nullable = false)
    private String address2;    // 나머지 상세 주소

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "is_email_verified")
    @ColumnDefault("false")
    private boolean isEmailVerified;

    @Column(name = "is_active")
    @ColumnDefault("true")
    private boolean isActive;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Orders> orders;
}