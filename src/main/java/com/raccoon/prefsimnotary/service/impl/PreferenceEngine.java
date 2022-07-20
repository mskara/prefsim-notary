package com.raccoon.prefsimnotary.service.impl;

import com.raccoon.prefsimnotary.event.PreferenceCreatedEvent;
import com.raccoon.prefsimnotary.model.entity.Notary;
import com.raccoon.prefsimnotary.model.entity.NotaryOffice;
import com.raccoon.prefsimnotary.model.entity.Term;
import com.raccoon.prefsimnotary.model.entity.User;
import com.raccoon.prefsimnotary.service.NotaryOfficeService;
import com.raccoon.prefsimnotary.service.NotaryService;
import com.raccoon.prefsimnotary.service.TermService;
import com.raccoon.prefsimnotary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PreferenceEngine {

    private final NotaryService notaryService;
    private final NotaryOfficeService notaryOfficeService;
    private final TermService termService;
    private Map<String, String> preferenceTable;

    @EventListener(PreferenceCreatedEvent.class)
    public void run() {

        final Term activeTerm = termService.getActiveTerm();
        final List<Notary> notaries = notaryService.getNotaryList(activeTerm);

        preferenceTable = notaryOfficeService
                .getActiveNotaryOfficeList()
                .parallelStream()
                .collect(Collectors.toMap(NotaryOffice::getNotaryOfficeCode, status -> ""));

        notaries.forEach(notary -> {

            final Set<NotaryOffice> notaryOfficeSet = notary.getPreference(activeTerm).getNotaryOfficeSet();

            findFirstAvailableNotaryOffice(notaryOfficeSet).ifPresent(
                    availableNotaryOffice -> {
                        notaryService.updateEstimatedNotaryOffice(notary, availableNotaryOffice);
                        preferenceTable.put(availableNotaryOffice.getNotaryOfficeCode(), notary.getUsername());
                    });
        });

    }

    private Optional<NotaryOffice> findFirstAvailableNotaryOffice(Set<NotaryOffice> notaryOfficeSet) {
        return notaryOfficeSet
                .stream()
                .filter(notaryOffice -> preferenceTable.get(notaryOffice.getNotaryOfficeCode()).isEmpty())
                .findFirst();
    }

}