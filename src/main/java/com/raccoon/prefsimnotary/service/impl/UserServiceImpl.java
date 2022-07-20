package com.raccoon.prefsimnotary.service.impl;


import com.raccoon.prefsimnotary.auth.TokenProvider;
import com.raccoon.prefsimnotary.exception.ApiError;
import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.dto.request.UserLoginRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import com.raccoon.prefsimnotary.model.entity.User;
import com.raccoon.prefsimnotary.model.enums.Permission;
import com.raccoon.prefsimnotary.model.enums.Status;
import com.raccoon.prefsimnotary.repository.UserRepository;
import com.raccoon.prefsimnotary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public AccessTokenResponseDto login(@Valid UserLoginRequestDto userLoginRequestDto) {
        final String username = userLoginRequestDto.getUsername();
        final String password = userLoginRequestDto.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generateToken(username);

    }

    @Override
    public AccessTokenResponseDto register(@Valid User user) {

        checkUsername(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserStatus(Status.ACTIVE);
        user.setPermissions(Permission.getNotaryUserPermissions());

        userRepository.save(user);

        return tokenProvider.generateToken(user.getUsername());
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new PrefsimException(ApiError.RESOURCE_NOT_FOUND, "User not found : " + username));
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private void checkUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new PrefsimException(ApiError.USER_ALREADY_EXISTS, "User already exists : " + username);
        }
    }


}