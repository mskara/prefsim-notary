package com.raccoon.prefsimnotary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrefsimException extends RuntimeException {
    private ApiError apiError;
    private String errorMessage;
}
