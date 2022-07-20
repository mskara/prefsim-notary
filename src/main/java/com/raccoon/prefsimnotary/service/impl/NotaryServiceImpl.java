package com.raccoon.prefsimnotary.service.impl;


import com.raccoon.prefsimnotary.event.PreferenceCreatedEventPublisher;
import com.raccoon.prefsimnotary.model.dto.request.PreferenceRequestDto;
import com.raccoon.prefsimnotary.model.dto.response.PreferenceResultResponseDto;
import com.raccoon.prefsimnotary.model.entity.*;
import com.raccoon.prefsimnotary.repository.NotaryRepository;
import com.raccoon.prefsimnotary.service.NotaryOfficeService;
import com.raccoon.prefsimnotary.service.NotaryService;
import com.raccoon.prefsimnotary.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotaryServiceImpl implements NotaryService {

    private final NotaryRepository notaryRepository;
    private final TermService termService;
    private final NotaryOfficeService notaryOfficeService;

    @Override
    public Notary getCurrentNotary() {
        return (Notary) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PreferenceCreatedEventPublisher
    @Override
    public void createPreference(PreferenceRequestDto preferenceRequestDto) {

        final Term activeTerm = termService.getActiveTerm();
        final Set<NotaryOffice> notaryOffices = notaryOfficeService
                .getActiveNotaryOfficeListSortedByIncomeDesc(preferenceRequestDto.getNotaryOfficeCodes());
        final Notary notary = getCurrentNotary();
        notary.addPreference(new Preference(activeTerm, notaryOffices));
        notaryRepository.save(notary);
    }


    @Override
    public List<Notary> getNotaryList(Term term) {
        return notaryRepository.findAll()
                .stream()
                .filter(notary -> notary.hasPreference(term))
                .sorted(Comparator.comparing(Notary::getRegisterNumber))
                .toList();
    }

    @Override
    public void updateEstimatedNotaryOffice(Notary notary, NotaryOffice notaryOffice) {

        notary.setEstimatedNotaryOffice(notaryOffice);
        notaryRepository.save(notary);

    }

    @Override
    public List<PreferenceResultResponseDto> getPreferenceResults() {
        return notaryRepository.findAll()
                .stream()
                .map(notary -> new PreferenceResultResponseDto(
                        notary.getFullName(),
                        notary.getEstimatedNotaryOffice()))
                .toList();
    }

}