package com.taskflow.global.filter;

import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import com.taskflow.global.config.customUserDetails.Service.CustomUserDetailsService;
import com.taskflow.global.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//인증용
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // 필터링 필요없는 URI 배열
    // 회원가입, 로그인
    private static final String[] FILTER_PASS_URI = {"/api/auth/login", "api/auth/signup"};
    private final CustomUserDetailsService customUserDetailsService;

    //필터링
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();

        //필터링 필요한 URI
        if(!isFilterPassUri(requestURI)){
            String token = jwtUtil.getToken(httpServletRequest.getHeader("Authorization"));
            //토큰 유무 확인
            if(token == null){
                //토큰 없으므로 예외 처리
                //아직 미구현_Spring Security 사용 시점에 구현 예정
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없습니다.");
                return;
            }

            //토큰 유효성 확인
            if(!jwtUtil.isTokenValid(token)){
                // 토큰이 유효하지 않으므로 예외처리
                //아직 미구현_Spring Security 사용 시점에 구현 예정
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "토큰이 유효하지 않습니다.");
                return;
            }

            //인증 객체 생성
            String email = jwtUtil.getEmail(token);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(email);
            // 인증 확인용으로
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    null,
                    customUserDetails.getAuthorities()
            );
            //인증 객체를 SecurityContextHolder에 등록
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
        //다음 필터
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // 필터링 필요 여부 확인: false일 경우 필터링 필요
    private boolean isFilterPassUri(String requestURI){
        // 필터링이 필요없는 uri일 경우 true 반환
        return PatternMatchUtils.simpleMatch(FILTER_PASS_URI, requestURI);
    }
}
