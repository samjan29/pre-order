package com.pre_order.product_service.global.exception.dto;

import com.pre_order.product_service.global.exception.ErrorCode;
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

    @Override
    public String toString() {
        return "{\"status\":" + status + ",\"message\":\"" + message + "\"}";
    }
}