package com.raccoon.prefsimnotary.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Files {

    TNB_NOTARY_LIST_FILE("notary-list.csv", "src/main/resources/files/"),
    TNB_INCOME_LIST_FILE("income-list.csv", "src/main/resources/files/");

    private final String fileName;
    private final String path;
}
