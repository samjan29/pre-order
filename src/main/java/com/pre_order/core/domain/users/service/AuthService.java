package com.pre_order.core.domain.users.service;

import com.pre_order.core.domain.users.dto.AuthCodeDto;
import com.pre_order.core.domain.users.dto.LoginRequestDto;
import com.pre_order.core.domain.users.entity.Users;
import com.pre_order.core.domain.users.repository.UsersCustomRepository;
import com.pre_order.core.domain.users.repository.UsersRepository;
import com.pre_order.core.global.exception.CustomException;
import com.pre_order.core.global.exception.ErrorCode;
import com.pre_order.core.global.security.encrypt.AES256Util;
import com.pre_order.core.global.security.jwt.JWTProvider;
import com.pre_order.core.global.security.user.CustomUser;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final UsersCustomRepository usersCustomRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final AES256Util aes256Util;
    private final JavaMailSender javaMailSender;
    private final StringRedisTemplate redisTemplate;

    public static final String PREFIX_VERIFIED = "verified";
    public static final String PREFIX_LOGIN = "login";

    public AuthCodeDto generateAuthCode(String prefix, String email) {
        final String authCode = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(authCode, prefix + ":" + email, Duration.ofDays(1L));
        final String encryptAuthCode = aes256Util.encrypt(authCode);
        return new AuthCodeDto(encryptAuthCode);
    }

    @Transactional
    public void emailVerified(AuthCodeDto authCodeDto) {
        final String decryptAuthCode = aes256Util.decrypt(authCodeDto.authCode());
        final String authCodeValue = redisTemplate.opsForValue().get(decryptAuthCode);
        if (authCodeValue == null) {
            throw new CustomException(ErrorCode.EXPIRED_AUTH_CODE);
        }

        final String email = checkPrefix(PREFIX_VERIFIED, authCodeValue);
        usersCustomRepository.updateIsEmailVerified(email);

        redisTemplate.delete(decryptAuthCode);
    }

    private String checkPrefix(String prefix, String authCodeValue) {
        final String[] split = authCodeValue.split(":");
        if (!split[0].equals(prefix)) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return split[1];
    }

    @Async("mailSenderExecutor")
    public void sendEmail(String email, String authCode) {
        final MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();

        try {
            mimeMailMessage.setSubject("이메일 인증");
            mimeMailMessage.setText("인증 코드: " + authCode);
            mimeMailMessage.setRecipients(Message.RecipientType.TO, email);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("이메일 전송 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.FAILED_SEND_EMAIL);
        }
    }

    public HttpHeaders login(LoginRequestDto loginRequestDto) {
        final Users users = usersRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if (!passwordEncoder.matches(loginRequestDto.password(), users.getPassword())) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        final String accessToken = jwtProvider.createAccessToken(users.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(JWTProvider.HEADER_STRING, JWTProvider.TOKEN_PREFIX + accessToken);
        return responseHeaders;
    }

    public void logout(CustomUser user) {
        /*
        TODO 로그아웃 처리
        Refresh token 제거나 등등?
         */
    }
}
