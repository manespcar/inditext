package com.inditex.core.rest.controller;

import com.pagonxt.gtscore.rest.catalogue.client.model.ErrorsDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvicePricesControllerTest {

    @Mock
    private WebRequest webRequest;
    @Mock
    private NoSuchElementException noSuchElementException;
    @Mock
    private AccessDeniedException accessDeniedException;
    @Mock
    private ConstraintViolationException constraintViolationException;

    @InjectMocks
    private AdvicePricesController advicePricesController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testHandleMethodArgumentNotValid() {
        final List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("objectName", "field", "defaultMessage"));
        final List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError("objectName", "defaultMessage"));
        final BindException bindException = mock(BindException.class);
        doReturn(fieldErrors).when(bindException).getFieldErrors();
        doReturn(objectErrors).when(bindException).getGlobalErrors();
        final MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(mock(
                MethodParameter.class), bindException);
        final ResponseEntity<Object> result = this.advicePricesController.handleMethodArgumentNotValid(
                methodArgumentNotValidException, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, this.webRequest);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals("[field, defaultMessage, objectName, defaultMessage]",
                errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void testHandleHttpMediaTypeNotSupported() {
        final HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException = mock(HttpMediaTypeNotSupportedException.class);
        when(httpMediaTypeNotSupportedException.getMessage()).thenReturn("httpMediaTypeNotSupportedException");
        final ResponseEntity<Object> result = this.advicePricesController.handleHttpMediaTypeNotSupported(
                httpMediaTypeNotSupportedException, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, this.webRequest);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals(httpMediaTypeNotSupportedException.getMessage(), errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void testHandleServletRequestBindingException() {
        final ServletRequestBindingException servletRequestBindingException = mock(ServletRequestBindingException.class);
        when(servletRequestBindingException.getMessage()).thenReturn("servletRequestBindingException");
        final ResponseEntity<Object> result = this.advicePricesController.handleServletRequestBindingException(
                servletRequestBindingException, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, this.webRequest);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals(servletRequestBindingException.getMessage(), errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void testHandleHttpRequestMethodNotSupported() {
        final HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException = mock(HttpRequestMethodNotSupportedException.class);
        when(httpRequestMethodNotSupportedException.getMessage()).thenReturn("httpRequestMethodNotSupportedException");
        final ResponseEntity<Object> result = this.advicePricesController.handleHttpRequestMethodNotSupported(
                httpRequestMethodNotSupportedException, HttpHeaders.EMPTY, HttpStatus.METHOD_NOT_ALLOWED,
                this.webRequest);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.METHOD_NOT_ALLOWED.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals(httpRequestMethodNotSupportedException.getMessage(),
                errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void testHandleUnexpectedException() {
        when(this.noSuchElementException.getMessage()).thenReturn("Unexpected server error");
        final ResponseEntity<Object> result = this.advicePricesController.handleUnexpectedException(this.noSuchElementException);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals(this.noSuchElementException.getMessage(), errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void testHandleValidationException() {
        final ValidationException validationException = mock(ValidationException.class);
        when(validationException.getMessage()).thenReturn("validationException");
        final ResponseEntity<Object> result = this.advicePricesController.handleValidationException(validationException);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals(validationException.getMessage(), errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void testHandleIllegalArgumentException() {
        final IllegalArgumentException illegalArgumentException = mock(IllegalArgumentException.class);
        when(illegalArgumentException.getMessage()).thenReturn("illegalArgumentException");
        final ResponseEntity<Object> result = this.advicePricesController
                .handleIllegalArgumentException(illegalArgumentException);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals(illegalArgumentException.getMessage(), errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void testHandleConstraintViolationException() {
        when(this.constraintViolationException.getMessage()).thenReturn("constraintViolationException");
        final ResponseEntity<Object> result = this.advicePricesController
                .handleConstraintViolationException(this.constraintViolationException);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
        assertEquals(this.constraintViolationException.getMessage(), errorsDTO.getErrors().get(0).getDescription());
    }

    @Test
    void handleMissingServletRequestParameterTest() {
        MissingServletRequestParameterException exceptionMock = mock(MissingServletRequestParameterException.class);
        ResponseEntity<Object> response = advicePricesController.handleMissingServletRequestParameter(exceptionMock,
                HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, this.webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testHandleTypeMismatchException() {
        final TypeMismatchException typeMismatchException = Mockito.mock(TypeMismatchException.class);
        when(typeMismatchException.getMessage()).thenReturn("typeMismatchException");
        final ResponseEntity<Object> result = this.advicePricesController.handleTypeMismatch(
                typeMismatchException, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, this.webRequest);
        final ErrorsDTO errorsDTO = (ErrorsDTO) result.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(errorsDTO);
        assertNotNull(errorsDTO.getErrors());
        assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), errorsDTO.getErrors().get(0).getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorsDTO.getErrors().get(0).getMessage());
    }

}