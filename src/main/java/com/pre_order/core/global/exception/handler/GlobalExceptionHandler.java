package com.pre_order.core.global.exception.handler;

import com.pre_order.core.global.exception.CustomException;
import com.pre_order.core.global.exception.ErrorCode;
import com.pre_order.core.global.exception.dto.ExceptionResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ExceptionResponseDto> handlerCustomException(CustomException exception) {
        final ErrorCode errorCode = exception.getErrorCode();
        log.error("errorCode: {}, message: {}", errorCode.getStatus(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(new ExceptionResponseDto(errorCode.getStatus().value(), errorCode.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    protected ResponseEntity<ExceptionResponseDto> handlerValidCustomException() {
        final ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        log.error("유효성 검사 통과 실패");
        return ResponseEntity.status(errorCode.getStatus()).body(new ExceptionResponseDto(errorCode));
    }

}