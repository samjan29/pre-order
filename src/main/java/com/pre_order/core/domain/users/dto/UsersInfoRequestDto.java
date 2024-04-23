package com.pre_order.core.domain.users.dto;

public record UsersInfoRequestDto (String email, String password, String name, String phoneNumber, String address1, String address2) {
    // TODO 제약조건 추가 e.g, 이메일 패턴, 비밀번호 패턴, 전화번호 패턴, 주소 패턴 등
}
