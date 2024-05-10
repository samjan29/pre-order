package com.pre_order.user_service.domain.users.controller;

import com.pre_order.user_service.domain.users.entity.Users;
import com.pre_order.user_service.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/users")
@RequiredArgsConstructor
public class InternalUsersController {

    private final UsersService usersService;

    @GetMapping("/user-id")
    public ResponseEntity<String> getUserId(@AuthenticationPrincipal Users users) {
        return ResponseEntity.ok(usersService.getUserId(users.getId()));
    }

}
