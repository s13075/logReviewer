package com.pjatkInz.logReviewer.configuration;

import com.pjatkInz.logReviewer.model.MyUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "Secret";
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean boolValidateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;

    }

}
