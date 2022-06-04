package com.raccoon.prefsimnotary.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class Term {

    @JsonIgnore
    @Id
    private String id;
    private Integer termCode;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonIgnore
    private Status status;



}
