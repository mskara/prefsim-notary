package com.raccoon.prefsimnotary.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime createdAt;
    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String message;

    @Setter
    private String path;

    @Setter
    private List<String> details;

    private ApiErrorResponse(ApiError apiError) {
        this.createdAt = LocalDateTime.now();
        this.httpStatus = apiError.getHttpStatus();
        this.errorCode = apiError.getErrorCode();
        this.message = apiError.getMessage();
    }

    public static ApiErrorResponse getInstance(ApiError apiError) {
        return new ApiErrorResponse(apiError);
    }

}