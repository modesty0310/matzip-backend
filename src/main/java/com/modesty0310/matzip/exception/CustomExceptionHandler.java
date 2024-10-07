package com.modesty0310.matzip.exception;

import com.modesty0310.matzip.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();

        // 필드 오류를 반복하여 에러 메시지 리스트에 추가
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(error.getDefaultMessage());
            System.out.println(error.getDefaultMessage());
        }

        Object message;

        // 에러가 하나일 때는 String으로 반환, 여러 개일 때는 String[]으로 반환
        if (errorMessages.size() == 1) {
            message = errorMessages.getFirst();
        } else {
            message = errorMessages.toArray(new String[0]);
        }

        // ErrorDTO 객체를 생성하여 리턴
        ErrorDTO errorDTO = new ErrorDTO(message, "Bad Request", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
