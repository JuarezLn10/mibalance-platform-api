package com.jrzln.mibalanceapi.shared.interfaces.rest.resources.response;

import lombok.Getter;

import java.time.Instant;

/**
 * Represents a standardized API error response.
 * Includes status code, error code, message, and timestamp.
 */
@Getter
public class ApiErrorResponse {

    private final String statusCode;
    private final String errorCode;
    private final String message;
    private final Instant timestamp;

    public ApiErrorResponse(String statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = Instant.now();
    }
}
