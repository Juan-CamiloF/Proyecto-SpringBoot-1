package com.springboot.backend.proyecto1.security.jwt;

import com.springboot.backend.proyecto1.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to get token on requests
 */
public class JwtTokenFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filter)
            throws ServletException, IOException {
        try {
            String token = getToken(req);
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error("Fallo método de filtrar las solicitudes {}", e.getMessage());
        }
        filter.doFilter(req, res);
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");
        return null;
    }
}
