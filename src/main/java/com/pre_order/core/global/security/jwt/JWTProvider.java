package com.pre_order.core.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pre_order.core.global.exception.CustomException;
import com.pre_order.core.global.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JWTProvider {
    private static final Long REFRESH_TOKEN_VALID_TIME = Duration.ofDays(14).toMillis();

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String ACCESS_TYPE = "access";
    public static final String REFRESH_TYPE = "refresh";
    public static final long EXPIRATION_TIME =  30 * 60 * 1000L;

    private static String secretKey;

    @Value("${jwt.secret.key}")
    public void setSecretKey(String secretKey) {
        JWTProvider.secretKey = secretKey;
    }

    public String createAccessToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String bringToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void validateToken(String token, String type) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
        } catch (SignatureException | SecurityException | MalformedJwtException e) {
            log.error("유효하지 않는 JWT 서명 입니다.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {

            if (type.equals(ACCESS_TYPE)) {
                final ErrorCode expiredAccessToken = ErrorCode.EXPIRED_ACCESS_TOKEN;
                log.error(expiredAccessToken.getMessage());
                throw new CustomException(expiredAccessToken);
            }

            if (type.equals(REFRESH_TYPE)) {
                final ErrorCode expiredRefreshToken = ErrorCode.EXPIRED_REFRESH_TOKEN;
                log.error(expiredRefreshToken.getMessage());
                throw new CustomException(expiredRefreshToken);
            }

        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰 입니다.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰 입니다. 토큰이 비었을 수 있으니 다시 확인해주세요.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String getUserInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("sub", String.class);
    }

    public String createRefreshToken() {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String expiredTokenCombo(String accessToken) {
        final String[] tokenParts = accessToken.split("\\.");
        validateExpiredTokenSignature(tokenParts);
        return getMemberIdByExpiredToken(tokenParts);
    }

    private void validateExpiredTokenSignature(String[] tokenParts) {
        final byte[] signatureBytes = Base64.getUrlDecoder().decode(tokenParts[2]);

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
            hmacSHA512.init(secretKeySpec);

            String headerPayload = tokenParts[0] + "." + tokenParts[1];
            byte[] hashed = hmacSHA512.doFinal(headerPayload.getBytes(StandardCharsets.UTF_8));

            if (!MessageDigest.isEqual(hashed, signatureBytes)) {
                log.error("유효하지 않은 JWT 서명");
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }

        } catch (NoSuchAlgorithmException e) {
            log.error("존재하지 않는 알고리즘");
        } catch (InvalidKeyException e) {
            log.error("유효하지 않는 Secret key 명세");
        }
    }

    private String getMemberIdByExpiredToken(String[] tokenParts) {
        final String payloadJson = new String(Base64.getDecoder().decode(tokenParts[1]));

        Map<?, ?> claims = null;
        try {
            claims = new ObjectMapper().readValue(payloadJson, Map.class);
            return (String) claims.get("sub");
        } catch (JsonMappingException e) {
            log.error("Json 매핑 실패");
        } catch (JsonProcessingException e) {
            log.error("Json 파싱 실패");
        }

        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

}