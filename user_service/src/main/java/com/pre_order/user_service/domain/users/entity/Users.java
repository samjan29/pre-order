package com.pre_order.user_service.domain.users.entity;

import com.pre_order.user_service.global.entity.CommonEntity;
import com.pre_order.user_service.global.security.encrypt.EncryptConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class Users extends CommonEntity {

    @Convert(converter = EncryptConverter.class)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "name", nullable = false)
    private String name;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "zip_code"/*, nullable = false*/)    // 나중에
    private String zipCode;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "address1", nullable = false)
    private String address1;    // 시도군구

    @Convert(converter = EncryptConverter.class)
    @Column(name = "address2", nullable = false)
    private String address2;    // 나머지 상세 주소

    @Column(name = "is_email_verified", columnDefinition = "tinyint default 0")
    private Boolean isEmailVerified;

    @Column(name = "is_active", columnDefinition = "tinyint default 1")
    @ColumnDefault("true")
    private Boolean isActive;

}