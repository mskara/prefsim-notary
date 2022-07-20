package com.raccoon.prefsimnotary.exception;


import lombok.Getter;

import java.util.List;

@Getter
public class PrefsimException extends RuntimeException {
    private final ApiError apiError;
    private final List<String> details;

    public PrefsimException(ApiError apiError, List<String> details) {
        this.apiError = apiError;
        this.details = details;
    }

    public PrefsimException(ApiError apiError, String detail) {
        this.apiError = apiError;
        this.details = List.of(detail);
    }
}
