package com.modesty0310.matzip.config;

import com.modesty0310.matzip._enum.ErrorCode;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.exception.CustomException;
import com.modesty0310.matzip.mapper.AuthMapper;
import com.modesty0310.matzip.service.AuthService;
import com.modesty0310.matzip.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            String token = null;
            String email = null;

            // token 추출
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }

            // 토큰이 존재하고 유효한 경우에만 이메일 추출
            if (token != null && jwtTokenProvider.validateToken(token)) {
                email = jwtTokenProvider.getEmailFromToken(token);
            } else {
                throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getUserByEmail(email);
                if (jwtTokenProvider.validateToken(token) && user != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, null
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // SecurityContext에 인증 정보 저장
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    // request에 사용자 정보 저장
                    request.setAttribute("user", user);
                }
            }

            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            // Set the response status and content for unauthorized access
            System.out.println(e.getMessage());
            response.setStatus(e.getStatus().value()); // 401
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8"); // 인코딩 설정
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\", \"statusCode\":" + e.getStatus().value() + "}");
            response.getWriter().flush(); // 즉시 응답 전송
        }
    }
}

