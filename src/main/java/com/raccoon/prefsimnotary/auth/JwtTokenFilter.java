package com.raccoon.prefsimnotary.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        final Consumer<String> authSetter = token -> SecurityContextHolder
                .getContext()
                .setAuthentication(getAuthenticationFromToken(token));
        try {

            //if request has bearer token extract and authenticate else clear context
            extractTokenFromRequest(request).ifPresentOrElse(authSetter, SecurityContextHolder::clearContext);

            //after authentication continue dispatcher
            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }


    private Authentication getAuthenticationFromToken(String token) {

        //resolve jwt and find username
        final String username = tokenProvider.getUsernameFromToken(token);

        //find user by username
        final UserDetails user = userDetailsService.loadUserByUsername(username);

        // generate authentication token for setting security context
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {

        final String bearerPrefix = "Bearer ";
        final String authHeaderKey = "Authorization";

        //if exists bearer token in Authorization header, extract and return else return empty
        return Optional.ofNullable(request.getHeader(authHeaderKey))
                .stream()
                .filter(authHeader -> authHeader.startsWith(bearerPrefix))
                .map(authHeader -> authHeader.substring(bearerPrefix.length()))
                .findAny();
    }
}