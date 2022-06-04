package com.raccoon.prefsimnotary.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApiErrorType {

    AUTHENTICATION(1),
    RESOURCE_NOT_FOUND(3),
    VALIDATION(7);

    private final int errorCode;
}
