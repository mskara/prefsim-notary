package com.raccoon.prefsimnotary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {

    @JsonIgnore
    @Id
    private String id;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime createdTime;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

}
