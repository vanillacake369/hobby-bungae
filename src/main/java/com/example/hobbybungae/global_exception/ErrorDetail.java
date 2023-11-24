package com.example.hobbybungae.global_exception;


import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorDetail {
    private String field;
    private String value;
    private String reason;

    public ErrorDetail(String field, String value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public static List<ErrorDetail> of(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(fieldError ->
                        new ErrorDetail(fieldError.getField(),
                                Objects.requireNonNull(fieldError.getRejectedValue()).toString(),
                                fieldError.getDefaultMessage()))
                .toList();
    }
}