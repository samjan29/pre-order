package com.pre_order.core.domain.users.controller;

import com.pre_order.core.domain.users.dto.AuthCodeDto;
import com.pre_order.core.domain.users.dto.LoginRequestDto;
import com.pre_order.core.domain.users.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PatchMapping("/email-verified")
    public ResponseEntity<Void> emailVerified(@Valid @RequestBody AuthCodeDto authCodeDto) {
        authService.emailVerified(authCodeDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.noContent().headers(authService.login(loginRequestDto)).build();
    }
}
