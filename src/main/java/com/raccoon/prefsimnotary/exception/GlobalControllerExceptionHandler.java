package com.raccoon.prefsimnotary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /*TODO: logging handled exception */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception, WebRequest request) {

        Map<String, String> fieldDetails = exception.getBindingResult().getFieldErrors()
                .stream()
                .filter(e -> e.getDefaultMessage() != null)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .httpStatus(ApiError.INVALID_PARAMETERS.getHttpStatus())
                .errorCode(ApiError.INVALID_PARAMETERS.getErrorCode())
                .message(ApiError.INVALID_PARAMETERS.getPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .details(fieldDetails)
                .build();

        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getHttpStatus());

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .httpStatus(ApiError.INSUFFICIENT_AUTHORITY.getHttpStatus())
                .errorCode(ApiError.INSUFFICIENT_AUTHORITY.getErrorCode())
                .message(ApiError.INSUFFICIENT_AUTHORITY.getPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getHttpStatus());

    }

    @ExceptionHandler(PrefsimException.class)
    public ResponseEntity<Object> handlePrefsimException(PrefsimException exception, WebRequest request) {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .httpStatus(exception.getApiError().getHttpStatus())
                .errorCode(exception.getApiError().getErrorCode())
                .message(exception.getErrorMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getHttpStatus());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {

        final int genericExceptionCode = 9999;
        final String genericExceptionMsg = "Some Error Occurred";

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(genericExceptionCode)
                .message(genericExceptionMsg)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        exception.printStackTrace();

        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getHttpStatus());

    }
}

