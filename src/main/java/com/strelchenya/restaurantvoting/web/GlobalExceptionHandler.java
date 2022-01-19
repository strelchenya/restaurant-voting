package com.strelchenya.restaurantvoting.web;

import com.strelchenya.restaurantvoting.error.ApplicationException;
import com.strelchenya.restaurantvoting.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists!";
    public static final String EXCEPTION_DUPLICATE_VOTE = "You have already voted!";
    public static final String EXCEPTION_DUPLICATE_MENU_ITEM = "This restaurant already has this menu item on the menu!";
    public static final String EXCEPTION_DUPLICATE_RESTAURANT = "This restaurant already exists!";

    private static final Map<String, String> CONTAINS_EXCEPTIONS = Map.of(
            "VOTE_UNIQUE_USER_DATE_IDX", EXCEPTION_DUPLICATE_VOTE,
            "USER_UNIQUE_EMAIL_IDX", EXCEPTION_DUPLICATE_EMAIL,
            "MENU_ITEM_UNIQUE_RESTAURANT_TITLE_IDX", EXCEPTION_DUPLICATE_MENU_ITEM,
            "RESTAURANT_UNIQUE_TITLE_IDX", EXCEPTION_DUPLICATE_RESTAURANT
    );

    private final ErrorAttributes errorAttributes;

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return handleBindingErrors(ex.getBindingResult(), request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return handleBindingErrors(ex.getBindingResult(), request);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> appException(WebRequest request, ApplicationException ex) {
        log.error("ApplicationException: {}", ex.getMessage());
        return createResponseEntity(getDefaultBody(request, ex.getOptions(), null), ex.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> persistException(WebRequest request, EntityNotFoundException ex) {
        log.error("EntityNotFoundException: {}", ex.getMessage());
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(MESSAGE), null),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataException(WebRequest request, DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        return createResponseEntity(
                getDefaultBody(request, ErrorAttributeOptions.of(MESSAGE), getErrorMessage(ex.getMessage())),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<Object> handleBindingErrors(BindingResult result, WebRequest request) {
        String msg = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.defaults(), msg),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private Map<String, Object> getDefaultBody(WebRequest request, ErrorAttributeOptions options, String msg) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, options);
        if (msg != null) {
            body.put("message", msg);
        }
        return body;
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponseEntity(Map<String, Object> body, HttpStatus status) {
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        return (ResponseEntity<T>) ResponseEntity.status(status).body(body);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex, Object body, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, @NonNull WebRequest request
    ) {
        log.error("Exception", ex);
        super.handleExceptionInternal(ex, body, headers, status, request);
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(),
                ValidationUtil.getRootCause(ex).getMessage()), status);
    }

    private static String getErrorMessage(String message) {
        log.info("getErrorMessage: {}", message);
        if (message == null || message.isBlank()) {
            return null;
        }
        return CONTAINS_EXCEPTIONS.entrySet().stream()
                .filter(e -> message.toUpperCase().contains(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
    }
}