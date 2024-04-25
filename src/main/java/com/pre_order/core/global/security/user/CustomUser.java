package com.pre_order.core.global.security.user;

import com.pre_order.core.domain.users.entity.Users;

public record CustomUser(Long id, String email, String name, String phoneNumber, String address1, String address2) {
    public CustomUser(Users users) {
        this(users.getId(), users.getEmail(), users.getName(), users.getPhoneNumber(), users.getAddress1(), users.getAddress2());
    }
}
