package com.pre_order.core.domain.users.controller;

import com.pre_order.core.domain.users.dto.AuthCodeDto;
import com.pre_order.core.domain.users.dto.LoginRequestDto;
import com.pre_order.core.domain.users.service.AuthService;
import com.pre_order.core.global.security.user.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PatchMapping(value = "/email-verified", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> emailVerified(@Valid @RequestBody AuthCodeDto authCodeDto) {
        authService.emailVerified(authCodeDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.noContent().headers(authService.login(loginRequestDto)).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUser user) {
        authService.logout(user);
        return ResponseEntity.noContent().build();
    }
}
