package com.pre_order.core.domain.users.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.pre_order.core.domain.users.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class UsersCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Long countUserByEmail(String email) {
        return jpaQueryFactory
                .select(users.count())
                .from(users)
                .where(users.email.eq(email)
                        .and(users.isActive.eq(true)))
                .fetchOne();
    }

    public Long countUserByPhoneNumber(String phoneNumber) {
        return jpaQueryFactory
                .select(users.count())
                .from(users)
                .where(users.phoneNumber.eq(phoneNumber)
                        .and(users.isActive.eq(true)))
                .fetchOne();
    }
}
