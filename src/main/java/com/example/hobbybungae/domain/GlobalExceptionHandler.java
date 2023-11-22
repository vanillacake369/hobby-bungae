package com.example.hobbybungae.domain;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.example.hobbybungae.domain.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> processValidationError(
        MethodArgumentNotValidException ex){
        log.info("입력값 검증 예외");
        BindingResult bindingResult = ex.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            log.info(fieldError.getField());
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }
        log.info(builder.toString());

//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//            new ErrorResponseDto(
//                builder.toString()
//            )
//        );
        ErrorResponseDto responseDto = new ErrorResponseDto(builder.toString());
        log.info(responseDto.toString());
        return new ResponseEntity<>(responseDto, BAD_REQUEST);

    }

}
