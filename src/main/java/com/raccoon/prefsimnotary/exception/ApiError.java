package com.raccoon.prefsimnotary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiError {

    BAD_CREDENTIALS(1001, ApiErrorType.AUTHENTICATION, HttpStatus.UNAUTHORIZED, "Bad Credentials!"),
    USER_ALREADY_EXISTS(1002, ApiErrorType.AUTHENTICATION, HttpStatus.CONFLICT, "User Already Exists!"),
    INVALID_TOKEN(1003, ApiErrorType.AUTHENTICATION, HttpStatus.UNAUTHORIZED, "Invalid Token!"),
    INSUFFICIENT_AUTHORITY(1004, ApiErrorType.AUTHENTICATION, HttpStatus.FORBIDDEN, "Insufficient Authority!"),

    USER_NOT_FOUND(3001, ApiErrorType.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, "User Not Found!"),
    NOTARY_OFFICE_NOT_FOUND(3002, ApiErrorType.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, "Notary Office Not Found!"),
    ACTIVE_TERM_NOT_FOUND(3003, ApiErrorType.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, "Active Term Not Found"),

    INVALID_PAYMENT_STATUS(7001, ApiErrorType.VALIDATION, HttpStatus.BAD_REQUEST, "Invalid Payment Status!"),
    INVALID_PREFERENCE_STATUS(7002, ApiErrorType.VALIDATION, HttpStatus.BAD_REQUEST, "Invalid Preference Status!"),
    INVALID_PARAMETERS(7007, ApiErrorType.VALIDATION, HttpStatus.BAD_REQUEST, "Invalid Parameters!");

    private final int errorCode;
    private final ApiErrorType errorType;
    private final HttpStatus httpStatus;
    private final String phrase;
}
