package com.raccoon.prefsimnotary.auth;

import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface TokenProvider {

    AccessTokenResponseDto generateToken(String username);

    String getUsernameFromToken(String token);

    List<SimpleGrantedAuthority> getRolesFromToken(String token);

}