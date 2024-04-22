package com.pre_order.core.domain.users.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsersInfoRequestDto {
    // TODO 제약조건 추가 e.g, 이메일 패턴, 비밀번호 패턴, 전화번호 패턴, 주소 패턴 등
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address1;
    private String address2;
}
