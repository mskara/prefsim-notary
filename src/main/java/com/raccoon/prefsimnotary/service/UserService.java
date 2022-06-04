package com.raccoon.prefsimnotary.service;

import com.raccoon.prefsimnotary.model.dto.request.PreferenceRequestDto;
import com.raccoon.prefsimnotary.model.dto.request.UserLoginRequestDto;
import com.raccoon.prefsimnotary.model.dto.request.UserRegisterRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.AccessTokenResponseDto;
import com.raccoon.prefsimnotary.model.dto.response.PreferenceResultResponseDto;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.User;

import java.util.List;

public interface UserService {

    AccessTokenResponseDto login(UserLoginRequestDto userLoginRequestDto);

    AccessTokenResponseDto register(UserRegisterRequestDto userRegisterRequestDto);

    List<User> getUserList();

    User getUser(String username);

    User getCurrentUser();

    User createPreference(PreferenceRequestDto preferenceRequestDto);

    List<PreferenceResultResponseDto> getAllUsersPreferenceResults();

    void updatePresumptiveNotaryOffice(User user, NotaryOffice notaryOffice);

}