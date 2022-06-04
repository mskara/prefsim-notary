package com.raccoon.prefsimnotary.auth;

import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final HandlerExceptionResolver exceptionResolver;

    public JwtTokenFilter(TokenProvider tokenProvider,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.tokenProvider = tokenProvider;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {

        try {
            final Consumer<AccessTokenResponseDto> authSetter = token -> SecurityContextHolder.getContext()
                    .setAuthentication(tokenProvider.getAuthentication(token));
            getTokenFromRequest(request).ifPresent(authSetter);
            filterChain.doFilter(request, response);
        } catch (PrefsimException exception) {
            SecurityContextHolder.clearContext();
            exceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private Optional<AccessTokenResponseDto> getTokenFromRequest(HttpServletRequest request) {

        final String bearerPrefix = "Bearer ";
        final String authHeaderKey = "Authorization";

        final Predicate<String> isBearer = authHeader -> authHeader.startsWith(bearerPrefix);
        final Function<String, String> extractToken = authHeader -> authHeader.substring(bearerPrefix.length());
        final Function<String, AccessTokenResponseDto> convertTokenResponse = AccessTokenResponseDto::new;

        return Optional.ofNullable(request.getHeader(authHeaderKey))
                .stream()
                .filter(isBearer)
                .map(extractToken)
                .map(convertTokenResponse)
                .findAny();
    }
}