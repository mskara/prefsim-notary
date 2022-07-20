package com.raccoon.prefsimnotary.service;

import com.raccoon.prefsimnotary.model.dto.request.UserLoginRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import com.raccoon.prefsimnotary.model.entity.User;

import java.util.List;

public interface UserService {

    AccessTokenResponseDto login(UserLoginRequestDto userLoginRequestDto);

    AccessTokenResponseDto register(User user);

    List<User> getUserList();

    User getUser(String username);

    User getCurrentUser();


}