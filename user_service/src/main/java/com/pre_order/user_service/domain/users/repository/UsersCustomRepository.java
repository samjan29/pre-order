package com.pre_order.user_service.domain.users.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.pre_order.user_service.domain.users.entity.QUsers.users;

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

    public void updateIsEmailVerified(String email) {
        jpaQueryFactory
                .update(users)
                .set(users.isEmailVerified, true)
                .where(users.email.eq(email))
                .execute();
    }

    public void updateAddressAndPhoneNumber(Long id, String address1, String address2, String phoneNumber) {
        jpaQueryFactory
                .update(users)
                .set(users.address1, address1)
                .set(users.address2, address2)
                .set(users.phoneNumber, phoneNumber)
                .where(users.id.eq(id))
                .execute();
    }

    public void updatePassword(Long id, String password) {
        jpaQueryFactory
                .update(users)
                .set(users.password, password)
                .where(users.id.eq(id))
                .execute();
    }
}
