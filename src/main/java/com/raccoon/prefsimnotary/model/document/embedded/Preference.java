package com.raccoon.prefsimnotary.model.document.embedded;

import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import com.raccoon.prefsimnotary.model.document.Term;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

@Data
@AllArgsConstructor
public class Preference {

    @DBRef
    private Term term;

    @DBRef
    private Set<NotaryOffice> notaryOfficeSet;

}