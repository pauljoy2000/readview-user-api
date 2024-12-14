package com.readview.user.service.exception.handler;

import com.readview.user.service.constants.Messages;
import com.readview.user.service.dto.ApiResponse;
import com.readview.user.service.exception.BadRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MalformedJwtException.class, SignatureException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResponse handleMalformedJwtException(Exception e) {
        return ApiResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(Messages.INVALID_ACCESS_TOKEN)
                .data(e.getMessage())
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResponse handleExpiredJwtException(ExpiredJwtException e) {
        return ApiResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(Messages.EXPIRED_ACCESS_TOKEN)
                .data(e.getMessage())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleBadRequestException(BadRequestException e) {
        return ApiResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse handleGenericException(Exception e) {
        return ApiResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Messages.INTERNAL_SERVER_ERROR)
                .data(e.getMessage())
                .build();
    }
}
