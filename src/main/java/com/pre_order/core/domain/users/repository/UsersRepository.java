package com.pre_order.core.domain.users.repository;

import com.pre_order.core.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long>{
}
