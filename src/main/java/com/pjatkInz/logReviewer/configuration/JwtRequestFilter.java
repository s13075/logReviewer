package com.pjatkInz.logReviewer.configuration;

import com.pjatkInz.logReviewer.service.MyUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MyUserDetailsService myUserDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, MyUserDetailsService myUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeaderValue = getAuthorizationHeaderValue(request);

        if(authorizationHeaderValue == null ||!authorizationHeaderValue.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = tokenWithoutBearerSufix(authorizationHeaderValue);
        if (token != null && jwtUtil.boolValidateToken(token)) {
            String username = jwtUtil.getUserNameFromJwtToken(token);
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);

    }

    private String getAuthorizationHeaderValue(HttpServletRequest request){
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private String tokenWithoutBearerSufix(String authorizationHeaderValue){
        return authorizationHeaderValue.substring(7);
    }
}
