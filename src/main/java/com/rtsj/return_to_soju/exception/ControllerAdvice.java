package com.rtsj.return_to_soju.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({
            DuplicateSignInException.class,
//            IllegalArgumentException.class,
            WebClientResponseException.class,
            NotFoundUserException.class,
            NoDataUploadedException.class,
            NotFoundCalenderException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseResult exceptionHandler(HttpServletRequest request, Exception e) {
        return new ErrorResponseResult(e);
    }

    @ExceptionHandler({
            JwtException.class,
            InvalidTokenException.class,
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponseResult authorizedExceptionHandler(HttpServletRequest request, Exception e) {
        return new ErrorResponseResult(e);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ErrorResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return new ErrorResponseResult(errorMessage);
    }

    @ExceptionHandler(
            InvalidWeekException.class
    )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseResult internalServerError(HttpServletRequest request, Exception e) {
        return new ErrorResponseResult(e);
    }
}
