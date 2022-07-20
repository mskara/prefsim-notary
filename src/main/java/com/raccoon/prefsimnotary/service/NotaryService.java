package com.raccoon.prefsimnotary.service;

import com.raccoon.prefsimnotary.model.dto.request.PreferenceRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.PreferenceResultResponseDto;
import com.raccoon.prefsimnotary.model.entity.Notary;
import com.raccoon.prefsimnotary.model.entity.NotaryOffice;
import com.raccoon.prefsimnotary.model.entity.Term;
import com.raccoon.prefsimnotary.model.entity.User;

import java.util.List;

public interface NotaryService {

    void createPreference(PreferenceRequestDto preferenceRequestDto);

    Notary getCurrentNotary();

    List<Notary> getNotaryList(Term term);

    void updateEstimatedNotaryOffice(Notary notary, NotaryOffice notaryOffice);

    List<PreferenceResultResponseDto> getPreferenceResults();


}