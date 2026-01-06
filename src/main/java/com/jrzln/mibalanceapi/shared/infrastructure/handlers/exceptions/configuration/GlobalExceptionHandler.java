package com.jrzln.mibalanceapi.shared.infrastructure.handlers.exceptions.configuration;

import com.jrzln.mibalanceapi.iam.domain.model.exceptions.InvalidPasswordException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UserNotFoundException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UserSaveFailedException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UsernameAlreadyExistsException;
import com.jrzln.mibalanceapi.shared.domain.model.exceptions.InvalidEmailFormatException;
import com.jrzln.mibalanceapi.shared.domain.model.exceptions.InvalidUserIdException;
import com.jrzln.mibalanceapi.shared.interfaces.rest.resources.response.ApiErrorResponse;
import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST controllers.
 * Maps specific exceptions to standardized API error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(
                        "404",
                        "USER_NOT_FOUND",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handle(InvalidPasswordException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiErrorResponse(
                        "401",
                        "INVALID_PASSWORD",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handle(UsernameAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "USERNAME_ALREADY_EXISTS",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserSaveFailedException.class)
    public ResponseEntity<ApiErrorResponse> handle(UserSaveFailedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "USER_SAVE_FAILED",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidCurrencyCodeException.class)
    public ResponseEntity<ApiErrorResponse> handle(InvalidCurrencyCodeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "INVALID_CURRENCY_CODE",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidWalletNameException.class)
    public ResponseEntity<ApiErrorResponse> handle(InvalidWalletNameException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "INVALID_WALLET_NAME",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidWalletTypeException.class)
    public ResponseEntity<ApiErrorResponse> handle(InvalidWalletTypeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "INVALID_WALLET_TYPE",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(WalletNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(
                        "404",
                        "WALLET_NOT_FOUND",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(WalletSaveFailedException.class)
    public ResponseEntity<ApiErrorResponse> handle(WalletSaveFailedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "WALLET_SAVE_FAILED",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<ApiErrorResponse> handle(InvalidEmailFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "INVALID_EMAIL_FORMAT",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public ResponseEntity<ApiErrorResponse> handle(InvalidUserIdException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        "400",
                        "INVALID_USER_ID",
                        ex.getMessage()
                ));
    }
}
