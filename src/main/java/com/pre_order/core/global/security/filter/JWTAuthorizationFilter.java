package com.pre_order.core.global.security.filter;

import com.pre_order.core.domain.users.entity.Users;
import com.pre_order.core.domain.users.repository.UsersRepository;
import com.pre_order.core.global.security.jwt.JWTProvider;
import com.pre_order.core.global.security.user.role.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final JWTProvider jwtProvider;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.bringToken(request);

        if (token != null) {

            jwtProvider.validateToken(token, JWTProvider.ACCESS_TYPE);
            Long userId = Long.valueOf(jwtProvider.getUserInfoFromToken(token));
            Users users = usersRepository.findById(userId).orElseThrow();

            forceAuthentication(users);
        }

        filterChain.doFilter(request, response);
    }

    // 강제로 로그인 처리하는 메소드
    private void forceAuthentication(Users users) {
        // 스프링 시큐리티 객체에 저장할 authentication 객체를 생성
        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        users,
                        null,
                        getAuthorities(users.getIsEmailVerified())
                );

        // 스프링 시큐리티 내에 우리가 만든 authentication 객체를 저장할 context 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // context에 authentication 객체를 저장
        context.setAuthentication(authentication);
        // 스프링 시큐리티에 context를 등록
        SecurityContextHolder.setContext(context);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(boolean isVerified) {
        String role = isVerified ? UserRole.VERIFIED_USER.getAuthority() : UserRole.UNVERIFIED_USER.getAuthority();
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);
        return authorities;
    }
}