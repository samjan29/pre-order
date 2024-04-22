package com.pre_order.core.domain.users.controller;

import com.pre_order.core.domain.users.dto.AuthCodeResponseDto;
import com.pre_order.core.domain.users.dto.UsersInfoRequestDto;
import com.pre_order.core.domain.users.service.AuthService;
import com.pre_order.core.domain.users.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthCodeResponseDto> signUp(@Valid @RequestBody UsersInfoRequestDto usersInfoRequestDto) {
        // TODO 두 서비스 메서드 트랜잭션 하나로 묶어서 처리하기
        usersService.signUp(usersInfoRequestDto);
        final AuthCodeResponseDto authCodeResponseDto = authService.generateAuthCode(usersInfoRequestDto.getEmail());
        authService.verifyEmail(usersInfoRequestDto.getEmail(), authCodeResponseDto.authCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(authCodeResponseDto);
    }
}
