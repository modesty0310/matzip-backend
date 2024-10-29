package com.modesty0310.matzip.exception;

import com.modesty0310.matzip.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    // valid error 여러개 나올 경우를 위한 핸들러
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

    // 커스텀 에러 처리 로직 사용을 위해 enum에 추가 필요
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getErrorMessage());
        response.put("error", ex.getStatus().getReasonPhrase());
        response.put("statusCode", ex.getStatus().value());

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // 예외 메시지를 로깅하거나 필요에 따라 다른 처리를 추가
        System.out.println("Exception caught: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: " + e.getMessage());
    }
}
