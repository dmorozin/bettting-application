package com.bettingapp.bettingapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExceptionDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;
}
