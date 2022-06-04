package com.raccoon.prefsimnotary.controller;

import com.raccoon.prefsimnotary.config.SwaggerConfig;
import com.raccoon.prefsimnotary.model.dto.request.PreferenceRequestDto;
import com.raccoon.prefsimnotary.model.dto.request.UserLoginRequestDto;
import com.raccoon.prefsimnotary.model.dto.request.UserRegisterRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import com.raccoon.prefsimnotary.model.dto.response.PreferenceResultResponseDto;
import com.raccoon.prefsimnotary.model.document.User;
import com.raccoon.prefsimnotary.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerConfig.USER_TAG})
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Give authentication token")
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponseDto> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        return ResponseEntity.ok(userService.login(userLoginRequestDto));
    }

    @ApiOperation(value = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<AccessTokenResponseDto> register(@Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        return ResponseEntity.ok(userService.register(userRegisterRequestDto));
    }

    @ApiOperation(value = "Display user list")
    @GetMapping
    @PreAuthorize("hasAuthority('PERM_VIEW_USER_LIST')")
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @ApiOperation(value = "Display user by username")
    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('PERM_VIEW_USER')")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @ApiOperation(value = "Display current user")
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('PERM_VIEW_CURRENT_USER')")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @ApiOperation(value = "Create new preference")
    @PostMapping("/preference")
    @PreAuthorize("hasAuthority('PERM_CREATE_PREFERENCE')")
    public ResponseEntity<User> createPreference(@Valid @RequestBody PreferenceRequestDto preferenceRequestDto) {
        return ResponseEntity.ok(userService.createPreference(preferenceRequestDto));
    }

    @ApiOperation(value = "Display preference results")
    @GetMapping("/preference-results")
    @PreAuthorize("hasAuthority('PERM_VIEW_PREFERENCE_RESULT')")
    public ResponseEntity<List<PreferenceResultResponseDto>> getAllUsersPreferenceResults() {
        return ResponseEntity.ok(userService.getAllUsersPreferenceResults());
    }

}