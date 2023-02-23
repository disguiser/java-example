package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.JwtUtilities;
import org.example.model.CheckResult;
import org.example.service.CustomerUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtUtilities jwtUtilities;
    private CustomerUserDetailsService customerUserDetailsService;

    public JwtAuthenticationFilter(JwtUtilities jwtUtilities, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtUtilities = jwtUtilities;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String responseText = null;
        if (!isEmpty(header) && header.startsWith("Bearer ")) {
            final String token = header.split(" ")[1].trim();
            if (!isEmpty(token)) {
                CheckResult checkResult = jwtUtilities.validateToken(token);
                if (checkResult.isSuccess()) {
                    String username = jwtUtilities.extractUsername(token);

                    UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails.getUsername() ,null , userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    filterChain.doFilter(request,response);
                    return;
                } else {
                    responseText = checkResult.getErrMsg();
                }
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(responseText!=null ? responseText : "invalid jwt token");
        filterChain.doFilter(request,response);
    }
}
