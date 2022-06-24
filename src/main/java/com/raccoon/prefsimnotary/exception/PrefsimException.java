package com.raccoon.prefsimnotary.exception;

import lombok.*;


@AllArgsConstructor
@Getter
public class PrefsimException extends RuntimeException {
    private ApiError apiError;
    private String errorMessage;
}
