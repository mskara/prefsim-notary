package com.raccoon.prefsimnotary.service;

import com.raccoon.prefsimnotary.model.document.Term;
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

    void createPreference(PreferenceRequestDto preferenceRequestDto);

    List<User> getNotaryUserListSortedByRegisterNumber(Term activeTerm);

    void updateEstimatedNotaryOffice(User user, NotaryOffice notaryOffice);

    List<PreferenceResultResponseDto> getPreferenceResults();


}