package com.pre_order.order_service.global.security.user.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    VERIFIED_USER("VERIFIED_USER"),
    UNVERIFIED_USER("UNVERIFIED_USER");

    private final String authority;
}
