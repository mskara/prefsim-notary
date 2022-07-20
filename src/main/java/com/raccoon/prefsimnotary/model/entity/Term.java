package com.raccoon.prefsimnotary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raccoon.prefsimnotary.model.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class Term extends BaseEntity {

    private Integer termCode;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonIgnore
    private Status status;


}
