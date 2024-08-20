package com.inditex.core.rest.controller;

import com.pagonxt.gtscore.rest.catalogue.client.model.ErrorDTO;
import com.pagonxt.gtscore.rest.catalogue.client.model.ErrorsDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * The class Advice controller.
 */
@ControllerAdvice(basePackages = "com.inditex.core.rest")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvicePricesController extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(AdvicePricesController.class);

    private static final String UNEXPECTED_SERVER_ERROR = "Unexpected server error";

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                               final HttpHeaders headers,
                                                               final HttpStatusCode status,
                                                               final WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        final List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        final List<String> errors = new ArrayList<>();

        for (final FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getField() + ", " + fieldError.getDefaultMessage());
        }

        for (final ObjectError objectError : globalErrors) {
            errors.add(objectError.getObjectName() + ", " + objectError.getDefaultMessage());
        }

        log.error("handleMethodArgumentNotValid", ex);
        return this.error(BAD_REQUEST, Integer.toString(BAD_REQUEST.value()),
                BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), errors.toString());
    }

    @Override
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatusCode status,
                                                                  final WebRequest request) {
        log.error("handleHttpMediaTypeNotSupported", ex);
        return this.error(BAD_REQUEST, Integer.toString(BAD_REQUEST.value()),
                BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(final ServletRequestBindingException ex,
                                                                       final HttpHeaders headers,
                                                                       final HttpStatusCode status,
                                                                       final WebRequest request) {
        log.error("handleServletRequestBindingException", ex);
        return this.error(BAD_REQUEST, Integer.toString(BAD_REQUEST.value()),
                BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                                                                      final HttpHeaders headers,
                                                                      final HttpStatusCode status,
                                                                      final WebRequest request) {
        log.error("handleHttpRequestMethodNotSupported", ex);
        return this.error(HttpStatus.METHOD_NOT_ALLOWED, Integer.toString(HttpStatus.METHOD_NOT_ALLOWED.value()),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex, final HttpHeaders headers,
            final HttpStatusCode status, final WebRequest request) {
        log.error("handleMissingServletRequestParameter", ex);
        return this.error(BAD_REQUEST, Integer.toString(BAD_REQUEST.value()),
                BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.FATAL.name(), ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
                                                     final HttpHeaders headers,
                                                     final HttpStatusCode status,
                                                     final WebRequest request) {
        log.error("handleTypeMismatch", ex);
        final String message = "Unexpected value '" + ex.getValue() + "' for the param '" + ex.getPropertyName() + "'";
        return this.error(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), message);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(final Exception ex) {
        log.error("handleUnexpectedException", ex);
        return this.error(HttpStatus.INTERNAL_SERVER_ERROR, Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ErrorDTO.LevelEnum.FATAL.name(), UNEXPECTED_SERVER_ERROR);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleValidationException(final ValidationException ex) {
        log.error("handleValidationException", ex);
        return this.error(BAD_REQUEST, Integer.toString(BAD_REQUEST.value()),
                BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.error("handleIllegalArgumentException", ex);
        return this.error(BAD_REQUEST, Integer.toString(BAD_REQUEST.value()),
                BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
        log.error("handleConstraintViolationException", ex);
        return this.error(BAD_REQUEST, Integer.toString(BAD_REQUEST.value()),
                BAD_REQUEST.getReasonPhrase(), ErrorDTO.LevelEnum.ERROR.name(), ex.getMessage());
    }

    private ResponseEntity<Object> error(final HttpStatus httpStatus, final String code, final String message,
                                         final String level, final String descriptions) {
        final List<ErrorDTO> errors = new ArrayList<>();
        final ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.code(code);
        errorDTO.description(descriptions);
        errorDTO.message(message);
        errorDTO.level(ErrorDTO.LevelEnum.fromValue(level));
        errors.add(errorDTO);

        final ErrorsDTO gtsErrorDTO = new ErrorsDTO();
        gtsErrorDTO.errors(errors);
        return new ResponseEntity<>(gtsErrorDTO, httpStatus);
    }

}
