package com.team10.whatis.global.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.entity.StatusCode;
import com.team10.whatis.global.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);
        String refreshToken = jwtUtil.resolveToken(request, JwtUtil.REFRESH_TOKEN);
        if(accessToken != null) {
            //Access 토큰 유효 시, security context에 인증 정보 저장
            if(jwtUtil.validateToken(accessToken)){
                    setAuthentication(jwtUtil.getUserInfoFromToken(accessToken));
            }
            // Access 토큰 만료
            else if (refreshToken != null) {
                // Refresh 토큰 유효
                if (Boolean.TRUE.equals(jwtUtil.validateRefreshToken(refreshToken))){
                    String userEmail = jwtUtil.getUserInfoFromToken(refreshToken);
                    //new accessToken 발급
                    String newAccessToken = jwtUtil.createToken(userEmail, JwtUtil.ACCESS_TOKEN);
                    //헤더에 새로운 Access 토큰 넣기
                    response.setHeader(JwtUtil.ACCESS_TOKEN,newAccessToken);
                    //Security context에 인증 정보 저장
                    String newToken = newAccessToken.substring(7);
                    setAuthentication(jwtUtil.getUserInfoFromToken(newToken));
                    log.info("새로운 토큰 생성 완료");
                }
                //Access & Refresh 토큰 만료시
                else {
                    log.info("토큰 에러 here");
                    jwtExceptionHandler(response, "유효하지 않은 토큰 입니다.", StatusCode.BAD_REQUEST);
                    return;
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String userEmail) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(userEmail);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String message, StatusCode statusCode) {
        log.info("토큰 에러");
        response.setStatus(statusCode.getStatusCode());
        response.setContentType("application/json; charset=utf8");
        try {
            String json = new ObjectMapper().writeValueAsString(ResponseDto.setBadRequest(message));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
