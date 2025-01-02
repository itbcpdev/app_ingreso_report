package com.itbcp.report.application.configuration;

import com.itbcp.report.shared.WebException;
import com.itbcp.report.shared.WebExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class WebHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(ex);
        WebExceptionResponse message = null;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            message = new WebExceptionResponse("Aviso", ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value());
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            message = new WebExceptionResponse("Aviso", ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        }
        if (ex instanceof WebException) {
            message = new WebExceptionResponse(((WebException) ex).getTitle(), ex.getMessage(), ((WebException) ex).getStatus());
        } else if (message == null) {
            message = new WebExceptionResponse("Aviso", "Se produjo un error, por favor inténtalo de nuevo más tarde.", -1);
        }

        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(message);
    }
}