package com.pre_order.order_service.global.security.filter;

import com.pre_order.order_service.domain.orders.client.UsersClient;
import com.pre_order.order_service.global.exception.ErrorCode;
import com.pre_order.order_service.global.security.encrypt.AES256Util;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final UsersClient usersClient;

    private final AES256Util aes256Util;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ResponseEntity<String> responseEntity = usersClient.getUserId(request.getHeader("Authorization"));

        if (responseEntity.getStatusCode().isError()) {
            response.sendError(ErrorCode.FORBIDDEN.getStatus().value(), ErrorCode.FORBIDDEN.getMessage());
            return;
        }

        String encryptUserId = responseEntity.getBody();
        Long userId = Long.valueOf(aes256Util.decrypt(encryptUserId));
        forceAuthentication(userId);

        filterChain.doFilter(request, response);
    }

    private void forceAuthentication(Long userId) {
        // 스프링 시큐리티 객체에 저장할 authentication 객체를 생성
        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        userId,
                        null,
                        null
                );

        // 스프링 시큐리티 내에 우리가 만든 authentication 객체를 저장할 context 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // context에 authentication 객체를 저장
        context.setAuthentication(authentication);
        // 스프링 시큐리티에 context를 등록
        SecurityContextHolder.setContext(context);
    }
}