package com.pre_order.core.domain.users.controller;

import com.pre_order.core.domain.users.dto.AuthCodeDto;
import com.pre_order.core.domain.users.dto.PasswordRequestDto;
import com.pre_order.core.domain.users.dto.UserInfoDto;
import com.pre_order.core.domain.users.dto.UsersInfoRequestDto;
import com.pre_order.core.domain.users.service.AuthService;
import com.pre_order.core.domain.users.service.UsersService;
import com.pre_order.core.global.security.user.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final AuthService authService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    // TODO 반환 타입 빼기 왜??코드를 보내지??????
    public ResponseEntity<AuthCodeDto> signUp(@Valid @RequestBody UsersInfoRequestDto usersInfoRequestDto) {
        final String email = usersInfoRequestDto.email();
        // TODO 두 서비스 메서드 트랜잭션 하나로 묶어서 처리하기
        usersService.signUp(usersInfoRequestDto);
        final AuthCodeDto authCodeDto = authService.generateAuthCode(AuthService.PREFIX_VERIFIED, email);
        authService.sendEmail(email, authCodeDto.authCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(authCodeDto);
    }

    @PatchMapping("/user-info")
    public ResponseEntity<UserInfoDto> updateUserInfo(@Valid @RequestBody UserInfoDto userInfoDto, @AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(usersService.updateUserInfo(userInfoDto, user));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordRequestDto password, @AuthenticationPrincipal CustomUser user) {
        usersService.updatePassword(password.password(), user);
        return ResponseEntity.noContent().build();
    }
}
