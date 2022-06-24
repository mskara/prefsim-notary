package com.raccoon.prefsimnotary.service.impl;


import com.raccoon.prefsimnotary.auth.TokenProvider;
import com.raccoon.prefsimnotary.event.PreferenceCreatedEvent;
import com.raccoon.prefsimnotary.event.PreferenceCreatedEventPublisher;
import com.raccoon.prefsimnotary.exception.ApiError;
import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.dto.request.PreferenceRequestDto;
import com.raccoon.prefsimnotary.model.dto.request.UserLoginRequestDto;
import com.raccoon.prefsimnotary.model.dto.request.UserRegisterRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import com.raccoon.prefsimnotary.model.dto.response.PreferenceResultResponseDto;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.Term;
import com.raccoon.prefsimnotary.model.document.User;
import com.raccoon.prefsimnotary.model.document.embedded.NotaryInfo;
import com.raccoon.prefsimnotary.model.document.embedded.Preference;
import com.raccoon.prefsimnotary.model.enums.Permission;
import com.raccoon.prefsimnotary.model.enums.Status;
import com.raccoon.prefsimnotary.repository.UserRepository;
import com.raccoon.prefsimnotary.service.NotaryOfficeService;
import com.raccoon.prefsimnotary.service.TermService;
import com.raccoon.prefsimnotary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final TermService termService;
    private final NotaryOfficeService notaryOfficeService;

    @Override
    public AccessTokenResponseDto login(@Valid UserLoginRequestDto userLoginRequestDto) {
        final String username = userLoginRequestDto.getUsername();
        final String password = userLoginRequestDto.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return tokenProvider.generateToken(username);
        } catch (AuthenticationException exception) {
            throw new PrefsimException(ApiError.BAD_CREDENTIALS, "Invalid username or password");
        }
    }

    @Override
    public AccessTokenResponseDto register(@Valid UserRegisterRequestDto userRegisterRequestDto) {

        checkUsername(userRegisterRequestDto.getUsername());

        final NotaryInfo notaryInfo = NotaryInfo.builder()
                .name(userRegisterRequestDto.getName() + " " + userRegisterRequestDto.getSurname())
                .notaryClass(userRegisterRequestDto.getNotaryClass())
                .registerNumber(userRegisterRequestDto.getRegisterNumber())
                .preferenceStatus(Status.ACTIVE)
                .paymentStatus(Status.ACTIVE)
                .build();

        final User user = User.builder()
                .username(userRegisterRequestDto.getUsername())
                .password(passwordEncoder.encode(userRegisterRequestDto.getPassword()))
                .email(userRegisterRequestDto.getEmail())
                .userStatus(Status.ACTIVE)
                .permissions(Permission.getNotaryUserPermissions())
                .notaryInfo(notaryInfo)
                .build();

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
                .orElseThrow(() -> new PrefsimException(ApiError.USER_NOT_FOUND, "User not found : " + username));
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PreferenceCreatedEventPublisher
    @Override
    public void createPreference(PreferenceRequestDto preferenceRequestDto) {

        final User currentUser = getCurrentUser();
        final NotaryInfo notaryInfo = currentUser.getNotaryInfo();
        final Term activeTerm = termService.getActiveTerm();

        final Set<NotaryOffice> notaryOffices = notaryOfficeService
                .getActiveNotaryOfficeListSortedByIncomeDesc(preferenceRequestDto.getNotaryOfficeCodes());

        notaryInfo.addPreference(new Preference(activeTerm, notaryOffices));

        userRepository.save(currentUser);
    }

    @Override
    public List<User> getNotaryUserListSortedByRegisterNumber(Term term) {

        return getUserList()
                .stream()
                .filter(User::isNotary)
                .filter(user -> user.getNotaryInfo().hasPreference(term))
                .sorted(Comparator.comparing(user -> user.getNotaryInfo().getRegisterNumber()))
                .toList();
    }

    @Override
    public void updateEstimatedNotaryOffice(User user, NotaryOffice notaryOffice) {

        user.getNotaryInfo().setEstimatedNotaryOffice(notaryOffice);
        userRepository.save(user);

    }

    @Override
    public List<PreferenceResultResponseDto> getPreferenceResults() {
        return getUserList()
                .stream()
                .map(user -> new PreferenceResultResponseDto(
                        user.getNotaryInfo().getName(),
                        user.getNotaryInfo().getEstimatedNotaryOffice()))
                .toList();
    }

    private void checkUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new PrefsimException(ApiError.USER_ALREADY_EXISTS, "User already exists : " + username);
        }
    }
}