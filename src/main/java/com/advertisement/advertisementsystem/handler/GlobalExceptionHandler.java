package com.advertisement.advertisementsystem.handler;

import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.exception.TokenExpirationException;
import com.advertisement.advertisementsystem.exception.TokenValidationException;
import com.advertisement.advertisementsystem.exception.UserExistenceException;
import com.advertisement.advertisementsystem.model.dto.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        final String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(error ->
                        String.format("%s: %s", ((FieldError) error).getField(), error.getDefaultMessage()))
                .reduce((a, b) -> a + "; " + b)
                .orElse("Undefined error message");

        log.warn(exception.getMessage(), exception);

        return generateErrorResponse(exception, HttpStatus.BAD_REQUEST, request, errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleEntityNotFoundException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        log.warn(exception.getMessage(), exception);

        return generateErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<Void>> handleAccessDeniedException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        log.warn(exception.getMessage(), exception);

        return generateErrorResponse(exception, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(UserExistenceException.class)
    public ResponseEntity<APIResponse<Void>> handleUserExistenceException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        log.warn(exception.getMessage(), exception);

        return generateErrorResponse(exception, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<APIResponse<Void>> handleTokenExpirationException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        log.warn(exception.getMessage(), exception);

        return generateErrorResponse(exception, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<APIResponse<Void>> handleTokenValidationException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        log.warn(exception.getMessage(), exception);

        return generateErrorResponse(exception, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<Void>> handleBadCredentialsException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        log.warn(exception.getMessage(), exception);

        return generateErrorResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleServerSideErrorException(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error(exception.getMessage(), exception);
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<APIResponse<Void>> generateErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            HttpServletRequest request
    ) {
        final APIResponse<Void> errorResponse = APIResponse.<Void>builder()
                .status(httpStatus.value())
                .message(exception.getMessage())
                .path(request.getServletPath())
                .data(null)
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private ResponseEntity<APIResponse<Void>> generateErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            HttpServletRequest request,
            String customMessage
    ) {
        final APIResponse<Void> errorResponse = APIResponse.<Void>builder()
                .status(httpStatus.value())
                .message(Optional.ofNullable(customMessage).orElse(exception.getMessage()))
                .path(request.getServletPath())
                .data(null)
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
