package com.rtsj.return_to_soju.common.auth.jwt;

import com.rtsj.return_to_soju.common.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//스프링 시큐리티 필터
// /login 요청 -> 로그인 요청시 동작하는 필터.. 하지만 formLogin disable해서 작동 안 함.
// 따라서 해당 필터를 등록해줘야 로그인 할 때 필터가 걸림.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {
    private final JwtProvider jwtProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        if(token != null && jwtProvider.validateToken(token)){
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
