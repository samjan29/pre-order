package com.pre_order.core.domain.users.service;

import com.pre_order.core.domain.users.dto.AuthCodeResponseDto;
import com.pre_order.core.global.exception.CustomException;
import com.pre_order.core.global.exception.ErrorCode;
import com.pre_order.core.global.security.encrypt.AES256Util;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AES256Util aes256Util;
    private final JavaMailSender javaMailSender;
    private final StringRedisTemplate redisTemplate;

    public AuthCodeResponseDto generateAuthCode(String email) {
        final String authCode = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(authCode, "verified:" + email, Duration.ofDays(1L));
        final String encryptAuthCode = aes256Util.encrypt(authCode);
        return new AuthCodeResponseDto(encryptAuthCode);
    }

    @Async("mailSenderExecutor")
    public void verifyEmail(String email, String encryptAuthCode) {
        sendEmail(email, encryptAuthCode);
    }

    private void sendEmail(String email, String authCode) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();

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
}
