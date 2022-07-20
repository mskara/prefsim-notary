package com.raccoon.prefsimnotary.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Object> handleAuthenticationException(JWTVerificationException exception, WebRequest request) {

        log.error(exception.getMessage());

        ApiErrorResponse response = ApiErrorResponse.getInstance(ApiError.INVALID_TOKEN);
        response.setPath(getRequestPath(request));

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.getInstance(ApiError.AUTHENTICATION);
        response.setPath(getRequestPath(request));

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.getInstance(ApiError.INSUFFICIENT_AUTHORITY);
        response.setPath(getRequestPath(request));
        response.setDetails(List.of(exception.getMessage()));

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception, WebRequest request) {

        List<String> details = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .toList();

        ApiErrorResponse response = ApiErrorResponse.getInstance(ApiError.INVALID_PARAMETERS);
        response.setPath(getRequestPath(request));
        response.setDetails(details);

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @ExceptionHandler(PrefsimException.class)
    public ResponseEntity<Object> handlePrefsimException(PrefsimException exception, WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.getInstance(exception.getApiError());
        response.setPath(getRequestPath(request));
        response.setDetails(exception.getDetails());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {

        log.error(exception.getMessage());

        ApiErrorResponse response = ApiErrorResponse.getInstance(ApiError.UNDEFINED_ERROR);
        response.setPath(getRequestPath(request));

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    private String getRequestPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}

