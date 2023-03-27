package com.notebookapp.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.notebookapp.dto.CustomErrorDto;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<CustomErrorDto> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(item -> ControllerExceptionHandler.convertToCustomErrorDto(item, request.getDescription(false)))
                .toList();
    }

    @ExceptionHandler(NotFoundCustomException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public CustomErrorDto handleNotFoundException(Exception ex, WebRequest request) {
        CustomErrorDto message = new CustomErrorDto(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return message;

    }

    private static CustomErrorDto convertToCustomErrorDto(ObjectError error, String description) {
        return new CustomErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                error.getDefaultMessage(),
                description);
    }
}