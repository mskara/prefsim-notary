package com.raccoon.prefsimnotary.model.entity;

import com.raccoon.prefsimnotary.model.entity.NotaryOffice;
import com.raccoon.prefsimnotary.model.entity.Term;
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