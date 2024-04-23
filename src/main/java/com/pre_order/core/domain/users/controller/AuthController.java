package com.pre_order.core.domain.users.controller;

import com.pre_order.core.domain.users.dto.AuthCodeDto;
import com.pre_order.core.domain.users.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PatchMapping("/email-verified")
    public ResponseEntity<Void> emailVerified(@Valid @RequestBody AuthCodeDto authCodeDto) {
        authService.emailVerified(authCodeDto);
        return ResponseEntity.noContent().build();
    }
}
