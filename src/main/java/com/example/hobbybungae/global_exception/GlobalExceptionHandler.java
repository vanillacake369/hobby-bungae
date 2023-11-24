package com.example.hobbybungae.global_exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다. HttpMessageConverter 에서 등록한 HttpMessageConverter
     * binding 못할경우 발생 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> processValidationError(
            MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException", ex);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.INVALID_PARAM, ex.getBindingResult());
        return new ResponseEntity<>(response, GlobalErrorCode.INVALID_PARAM.getStatusCode());
    }
}
