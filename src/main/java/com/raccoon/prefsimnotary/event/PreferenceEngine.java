package com.raccoon.prefsimnotary.event;

import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.Term;
import com.raccoon.prefsimnotary.model.document.User;
import com.raccoon.prefsimnotary.model.document.embedded.Preference;
import com.raccoon.prefsimnotary.service.NotaryOfficeService;
import com.raccoon.prefsimnotary.service.TermService;
import com.raccoon.prefsimnotary.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
@AllArgsConstructor
public class PreferenceEngine {

    private final UserService userService;
    private final NotaryOfficeService notaryOfficeService;
    private final TermService termService;

    @EventListener
    public void run(PreferenceCreatedEvent event) {

        User runnerUser = (User) event.getSource();

        log.info(runnerUser.getUsername() + " is trigger preference engine!");

        final Term activeTerm = termService.getActiveTerm();
        final Map<String, String> preferenceTable = getEmptyPreferenceTable();

        log.info("Finding users who are notaries and have active term preference");

        List<User> users = new ArrayList<>(userService.getUserList()
                .stream()
                .filter(User::isNotary)
                .filter(user -> user.getNotaryInfo().hasPreferences())
                .filter(user -> user.getNotaryInfo().getPreferences()
                        .stream()
                        .anyMatch(preference -> preference.getTerm().equals(activeTerm)))
                .toList());

        users.sort(Comparator.comparing(user -> user.getNotaryInfo().getRegisterNumber()));

        log.info("Preference engine is running for " + users.size() + " users and " + activeTerm.getTermCode() + " term!");

        users.forEach(user ->
                user.getNotaryInfo().getPreferences()
                        .stream()
                        .filter(preference -> preference.getTerm().equals(activeTerm))
                        .map(Preference::getNotaryOfficeSet)
                        .findFirst()
                        .flatMap(notaryOffices -> findAvailableNotaryOffice(notaryOffices, preferenceTable))
                        .ifPresent(availableNotaryOffice -> {
                            userService.updatePresumptiveNotaryOffice(user, availableNotaryOffice);
                            preferenceTable.put(availableNotaryOffice.getNotaryOfficeCode(), user.getUsername());
                            log.info(user.getNotaryInfo().getName() + " -> " + availableNotaryOffice.getName());
                        }));

        log.info("Preference engine finished success!");
    }

    private Optional<NotaryOffice> findAvailableNotaryOffice(Set<NotaryOffice> notaryOffices,
                                                             Map<String, String> preferenceTable) {
        for (NotaryOffice notaryOffice : notaryOffices) {
            if (preferenceTable.get(notaryOffice.getNotaryOfficeCode()).isEmpty())
                return Optional.of(notaryOffice);
        }
        return Optional.empty();
    }

    private Map<String, String> getEmptyPreferenceTable() {
        return notaryOfficeService.getActiveNotaryOfficeList()
                .parallelStream()
                .collect(Collectors.toMap(NotaryOffice::getNotaryOfficeCode, status -> ""));
    }
}