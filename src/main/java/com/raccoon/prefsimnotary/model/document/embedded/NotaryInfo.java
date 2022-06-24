package com.raccoon.prefsimnotary.model.document.embedded;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raccoon.prefsimnotary.exception.ApiError;
import com.raccoon.prefsimnotary.exception.PrefsimException;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.Term;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Builder
@Data
public class NotaryInfo {

    private String name;
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
                .orElseThrow(() -> new PrefsimException(ApiError.PREFERENCE_NOT_FOUND,
                        "There is no preference for " + name + " in " + term.getTermCode()));
    }

    @JsonIgnore
    public void addPreference(Preference newPreference) {

        if (!this.paymentStatus.equals(Status.ACTIVE)) {
            throw new PrefsimException(ApiError.INVALID_PAYMENT_STATUS,
                    "Notary payment status is not available to create preference.");
        }

        if (!this.preferenceStatus.equals(Status.ACTIVE)) {
            throw new PrefsimException(ApiError.INVALID_PREFERENCE_STATUS,
                    "Notary preference status is not available to create preference.");
        }

        if (!isNotaryClassAvailable(newPreference)) {
            throw new PrefsimException(ApiError.INVALID_PREFERENCE_STATUS,
                    "Notary class can not be lower than notary office class!");
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
