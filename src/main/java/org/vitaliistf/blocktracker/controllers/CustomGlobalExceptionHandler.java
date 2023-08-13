package org.vitaliistf.blocktracker.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.vitaliistf.blocktracker.dto.response.ErrorResponse;
import org.vitaliistf.blocktracker.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotEnoughCoinsException.class)
    public ResponseEntity<ErrorResponse> notEnoughCoins(NotEnoughCoinsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CoinNotSupportedException.class)
    public ResponseEntity<ErrorResponse> coinNotSupported(CoinNotSupportedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchElement(NoSuchElementException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> permissionDenied(PermissionDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ItemExistsException.class)
    public ResponseEntity<ErrorResponse> itemExists(ItemExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConfirmationTokenNotFoundException.class)
    public String globalExceptionHandler(ConfirmationTokenNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("message", "Please, try again");
        return "error";
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                errors,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(Exception.class)
    public String globalExceptionHandler(Exception ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("message", "Please contact us.");
        return "error";
    }
}

