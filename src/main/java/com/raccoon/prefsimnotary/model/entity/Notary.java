package com.raccoon.prefsimnotary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raccoon.prefsimnotary.exception.ApiError;
import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Getter
@Setter
public class Notary extends User {

    private Integer notaryClass;
    private Integer registerNumber;
    private Status preferenceStatus;
    private Status paymentStatus;
    private Set<Preference> preferences;

    @DBRef
    private NotaryOffice currentNotaryOffice;

    @DBRef
    private NotaryOffice estimatedNotaryOffice;

    @JsonIgnore
    public boolean hasPreference(Term term) {

        if (this.preferences == null) {
            return false;
        }
        return this.preferences
                .stream()
                .anyMatch(preference -> preference.getTerm().equals(term));
    }

    @JsonIgnore
    public Preference getPreference(Term term) {
        return this.preferences
                .stream()
                .filter(preference -> preference.getTerm().equals(term))
                .findFirst()
                .orElseThrow(() -> new PrefsimException(ApiError.RESOURCE_NOT_FOUND,
                        List.of("There is no preference for " + getFullName() + " in " + term.getTermCode())));
    }

    @JsonIgnore
    public void addPreference(Preference newPreference) {

        final List<String> preferenceValidations = new ArrayList<>();

        if (!this.paymentStatus.equals(Status.ACTIVE)) {
            preferenceValidations.add("Notary payment status is not available to create preference.");
        }

        if (!this.preferenceStatus.equals(Status.ACTIVE)) {
            preferenceValidations.add("Notary preference status is not available to create preference.");
        }

        if (!isNotaryClassAvailable(newPreference)) {
            preferenceValidations.add("Notary class can not be lower than notary office class!");
        }

        if (!preferenceValidations.isEmpty()) {
            throw new PrefsimException(ApiError.INVALID_PREFERENCE_STATUS, preferenceValidations);
        }

        if (this.preferences != null) {
            this.preferences.removeIf(preference -> preference.getTerm().equals(newPreference.getTerm()));
            getPreferences().add(newPreference);
        } else {
            this.preferences = new HashSet<>();
            this.preferences.add(newPreference);
        }
    }

    private boolean isNotaryClassAvailable(Preference newPreference) {

        Predicate<NotaryOffice> notaryClassLessThanOrEqualToNotaryOfficeClass =
                notaryOffice -> this.notaryClass <= notaryOffice.getNotaryOfficeClass();

        return newPreference.getNotaryOfficeSet()
                .stream()
                .allMatch(notaryClassLessThanOrEqualToNotaryOfficeClass);
    }

}
