package com.pre_order.order_service.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),

    // Users
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "Duplicated Email"),
    DUPLICATED_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "Duplicated Phone Number"),

    // Auth
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"Expired Access Token"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Expired Refresh Token"),
    FAILED_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Failed Send Email"),
    EXPIRED_AUTH_CODE(HttpStatus.BAD_REQUEST, "Expired Auth Code"),

    // Orders
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "Invalid Quantity"),
    ;

    private final HttpStatus status;
    private final String message;
}
