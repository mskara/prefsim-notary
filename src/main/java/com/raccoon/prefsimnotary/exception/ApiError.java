package com.raccoon.prefsimnotary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiError {

    AUTHENTICATION(1001, HttpStatus.UNAUTHORIZED, "Incorrect username or password!"),
    INVALID_TOKEN(1002, HttpStatus.UNAUTHORIZED, "Invalid Token!"),
    INSUFFICIENT_AUTHORITY(1003, HttpStatus.FORBIDDEN, "Insufficient Authority!"),
    INVALID_PARAMETERS(2001, HttpStatus.BAD_REQUEST, "Invalid Parameters!"),
    USER_ALREADY_EXISTS(7001, HttpStatus.CONFLICT, "User Already Exists!"),
    RESOURCE_NOT_FOUND(7002, HttpStatus.NOT_FOUND, "Resource Not Found!"),
    INVALID_PREFERENCE_STATUS(7003, HttpStatus.BAD_REQUEST, "Invalid Preference Status!"),
    UNDEFINED_ERROR(9999, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Service Error Occurred!");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}