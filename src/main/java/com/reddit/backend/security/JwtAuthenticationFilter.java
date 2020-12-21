package com.reddit.backend.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProviderService jwtProviderService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            //Parsing JWT from Oncoming Request
            String jwtFromComingRequest = null;

            String containBearerToken = httpServletRequest.getHeader("Authorization");

            if (null != containBearerToken && StringUtils.hasText("containBearerToken")) {
                jwtFromComingRequest = containBearerToken.substring(7);
            }


            // Validating Jwt with public key , getting certificate from it
            boolean validateJwtToken = jwtProviderService.validateJwtToken(jwtFromComingRequest);

            // Extracting Username from UserDetails from its serviceImpl , adding it to Context

            if (StringUtils.hasText(jwtFromComingRequest) && validateJwtToken) {
                String usernameFromJwt = jwtProviderService.getUsernameFromJwt(jwtFromComingRequest);

                UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromJwt);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
