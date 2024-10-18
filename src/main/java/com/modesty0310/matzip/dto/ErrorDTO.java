package com.modesty0310.matzip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private Object message;
    private String error;
    private int statusCode;
}

