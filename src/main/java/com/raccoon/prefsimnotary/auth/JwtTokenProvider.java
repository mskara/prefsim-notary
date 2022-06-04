package com.raccoon.prefsimnotary.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.raccoon.prefsimnotary.exception.ApiError;
import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.tokenValidity}")
    private int tokenValidity;

    @Value("${jwt.signingKey}")
    private String signingKey;

    private final UserDetailsService userDetailsService;

    @Override
    public AccessTokenResponseDto generateToken(String username) {

        final long now = System.currentTimeMillis();
        final long expiryTime = now + tokenValidity;
        final String jwtToken = JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(expiryTime))
                .sign(Algorithm.HMAC256(signingKey.getBytes()));

        return new AccessTokenResponseDto(jwtToken);
    }

    @Override
    public Authentication getAuthentication(AccessTokenResponseDto accessTokenResponseDto) {
        final String username = getUsernameFromToken(accessTokenResponseDto.getToken());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String getUsernameFromToken(String token) {
        return decodeToken(token).getSubject();
    }

    private DecodedJWT decodeToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(signingKey))
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new PrefsimException(ApiError.INVALID_TOKEN, "Token can be expired or invalid");
        }

    }
}