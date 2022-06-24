package com.raccoon.prefsimnotary.service.impl;

import com.raccoon.prefsimnotary.event.PreferenceCreatedEvent;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.Term;
import com.raccoon.prefsimnotary.model.document.User;
import com.raccoon.prefsimnotary.service.NotaryOfficeService;
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

    private final UserService userService;
    private final NotaryOfficeService notaryOfficeService;
    private final TermService termService;
    private Map<String, String> preferenceTable;

    @EventListener(PreferenceCreatedEvent.class)
    public void run() {

        final Term activeTerm = termService.getActiveTerm();
        final List<User> users = userService.getNotaryUserListSortedByRegisterNumber(activeTerm);

        preferenceTable = notaryOfficeService.getActiveNotaryOfficeList()
                .parallelStream()
                .collect(Collectors.toMap(NotaryOffice::getNotaryOfficeCode, status -> ""));

        users.forEach(user -> {
            final Set<NotaryOffice> notaryOfficeSet = user
                    .getNotaryInfo()
                    .getPreference(activeTerm)
                    .getNotaryOfficeSet();

            findFirstAvailableNotaryOffice(notaryOfficeSet).ifPresent(availableNotaryOffice -> {
                userService.updateEstimatedNotaryOffice(user, availableNotaryOffice);
                preferenceTable.put(availableNotaryOffice.getNotaryOfficeCode(), user.getUsername());
            });
        });

    }

    private Optional<NotaryOffice> findFirstAvailableNotaryOffice(Set<NotaryOffice> notaryOfficeSet) {
        return notaryOfficeSet.stream()
                .filter(notaryOffice -> preferenceTable.get(notaryOffice.getNotaryOfficeCode()).isEmpty())
                .findFirst();
    }

}