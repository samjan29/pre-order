package com.pre_order.core.global.exception.dto;

import com.pre_order.core.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponseDto {
    private final int status;
    private final String message;

    public ExceptionResponseDto(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }
}