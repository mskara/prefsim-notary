package com.raccoon.prefsimnotary.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raccoon.prefsimnotary.model.document.NotaryOffice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceResultResponseDto {

    private String name;

    @JsonProperty("estimatedNotaryOffice")
    private NotaryOffice notaryOffice;

}
