package com.raccoon.prefsimnotary.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class PreferenceRequestDto {

    @Size(min = 1, max = 25, message = "There must be at least 1, most 25 notary office code in the list!")
    @NotNull(message = "Notary office code list can not be null!")
    private Set<String> notaryOfficeCodes;

}