package com.pre_order.order_service.domain.orders.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "usersClient", url = "http://localhost:8080/api/internal/users")
public interface UsersClient {

    @GetMapping("/user-id")
    ResponseEntity<String> getUserId(@RequestHeader("Authorization") String token);

}
