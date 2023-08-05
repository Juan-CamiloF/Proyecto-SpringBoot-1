package com.springboot.backend.proyecto1.security.jwt;

import com.springboot.backend.proyecto1.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Create token and validation methods
 */
@Component
public class JwtProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication auth) {
        UserDetailsImpl userDetail = (UserDetailsImpl) auth.getPrincipal();
        long hours = 24;
        return Jwts.builder().setIssuedAt(new Date())
                .setSubject(userDetail.getUsername()).setExpiration(new Date(new Date().getTime() + expiration * hours))
                .signWith(SignatureAlgorithm.HS512, secret).claim("authorities",userDetail.getAuthorities()).compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token mal formado {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Token vacio {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Fallo en la firma {}", e.getMessage());
        }
        return false;
    }

}
