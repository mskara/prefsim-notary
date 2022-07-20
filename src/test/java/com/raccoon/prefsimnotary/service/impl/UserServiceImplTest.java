package com.raccoon.prefsimnotary.service.impl;

import com.raccoon.prefsimnotary.auth.TokenProvider;
import com.raccoon.prefsimnotary.model.entity.User;
import com.raccoon.prefsimnotary.model.entity.Notary;
import com.raccoon.prefsimnotary.model.dto.request.UserLoginRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import com.raccoon.prefsimnotary.model.enums.Permission;
import com.raccoon.prefsimnotary.model.enums.Status;
import com.raccoon.prefsimnotary.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TermServiceImpl termService;
    @Mock
    private NotaryOfficeServiceImpl notaryOfficeService;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void whenValidUsernameAndPassword_thenNoConstraintViolations() {
        UserLoginRequestDto loginRequestDto = new UserLoginRequestDto();
        loginRequestDto.setUsername("test-user");
        loginRequestDto.setPassword("test-password");

        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(loginRequestDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenBlankUsernameAndPassword_thenTwoConstraintViolations() {
        UserLoginRequestDto loginRequestDto = new UserLoginRequestDto();
        loginRequestDto.setUsername(" ");
        loginRequestDto.setPassword(" ");

        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(loginRequestDto);
        assertEquals(2, violations.size());
    }

    @Test
    public void whenValidRegisterInfos_thenNoConstraintViolations() {

        User user = new User();
        user.setUsername("test-username");
        user.setPassword("test-password");
        user.setEmail("test-user@email.com");
        user.setFirstName("test-name");
        user.setLastName("test-surname");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenNullRegisterInfos_thenFiveConstraintViolations() {

        User user = new User();
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setFirstName(null);
        user.setLastName(null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(5, violations.size());

    }

    @Test
    public void registerTest() {

//        //prepare
//        Mockito.when(userRepository.existsByUsername("test-username")).thenReturn(false);
//        Mockito.when(passwordEncoder.encode("test-password")).thenReturn("test-password-encrypted");
//
//        final Notary mockNotary = Notary.builder()
//                .name("test-name test-surname")
//                .notaryClass(1)
//                .registerNumber(12345)
//                .preferenceStatus(Status.ACTIVE)
//                .paymentStatus(Status.ACTIVE)
//                .build();
//
//        final User mockUser = User.builder()
//                .username("test-username")
//                .password("test-password-encrypted")
//                .email("test-user@email.com")
//                .userStatus(Status.ACTIVE)
//                .permissions(Permission.getNotaryUserPermissions())
//                .notary(mockNotary)
//                .build();
//
//        Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
//
//        AccessTokenResponseDto mockToken = new AccessTokenResponseDto("test-user-token");
//        Mockito.when(tokenProvider.generateToken("test-username")).thenReturn(mockToken);
//
//        //execute
//        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
//        userRegisterRequestDto.setUsername("test-username");
//        userRegisterRequestDto.setPassword("test-password");
//        userRegisterRequestDto.setEmail("test-user@email.com");
//        userRegisterRequestDto.setName("test-name");
//        userRegisterRequestDto.setSurname("test-surname");
//        userRegisterRequestDto.setNotaryClass(1);
//        userRegisterRequestDto.setRegisterNumber(12345);
//
//
//        AccessTokenResponseDto token = userService.register(userRegisterRequestDto);
//
//        //check
//        assertEquals(mockToken, token);
//        Mockito.verify(userRepository).existsByUsername("test-username");
//        Mockito.verify(passwordEncoder).encode("test-password");
//        Mockito.verify(userRepository).save(mockUser);
//        Mockito.verify(tokenProvider).generateToken("test-username");
//
    }
}