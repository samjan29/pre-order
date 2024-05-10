package com.pre_order.order_service.global.security.entrypoint;

import com.pre_order.order_service.global.exception.ErrorCode;
import com.pre_order.order_service.global.exception.dto.ExceptionResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode forbidden = ErrorCode.FORBIDDEN;
        response.setCharacterEncoding("UTF-8");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(forbidden.getStatus().value());
        response.getWriter().append(new ExceptionResponseDto(forbidden).toString());
    }
}