package com.pre_order.user_service.domain.users.repository;

import com.pre_order.user_service.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByEmail(String email);
}
