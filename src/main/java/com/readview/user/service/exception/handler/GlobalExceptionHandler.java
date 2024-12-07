package com.readview.user.service.exception.handler;

import com.readview.user.service.constants.Messages;
import com.readview.user.service.dto.ApiResponse;
import com.readview.user.service.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception e) {
        return ResponseEntity.internalServerError().body(
                ApiResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Messages.INTERNAL_SERVER_ERROR)
                .data(e.getMessage())
                .build()
        );
    }
}
