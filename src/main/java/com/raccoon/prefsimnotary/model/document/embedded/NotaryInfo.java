package com.raccoon.prefsimnotary.model.document.embedded;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

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
    private NotaryOffice presumptiveNotaryOffice;

    @JsonIgnore
    public boolean isPaymentEnabled() {
        return getPreferenceStatus().equals(Status.ACTIVE);
    }

    @JsonIgnore
    public boolean isPreferenceEnabled() {
        return getPreferenceStatus().equals(Status.ACTIVE);
    }

    @JsonIgnore
    public boolean hasPreferences() {
        return getPreferences() != null;
    }

}
