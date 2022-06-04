package com.raccoon.prefsimnotary.auth;

import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import org.springframework.security.core.Authentication;

public interface TokenProvider {

    AccessTokenResponseDto generateToken(String username);

    Authentication getAuthentication(AccessTokenResponseDto accessTokenResponseDto);

}