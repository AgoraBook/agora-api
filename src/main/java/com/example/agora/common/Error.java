package com.example.agora.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Error {

    // Default
    ERROR(HttpStatus.BAD_REQUEST, "Request processing failed"),

    // 400 BAD REQUEST
    INVALID_JSON_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "입력 형식이 맞지 않습니다."),

    // 401 UNAUTHORIZED
    INVALID_ID_PASSWORD_EXCEPTION(HttpStatus.UNAUTHORIZED, "ID/password error"),
    INVALID_GOOGLE_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "Invalid Google Token"),
    INVALID_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "Invalid JWT"),
    LOG_OUT_JWT_TOKEN(HttpStatus.UNAUTHORIZED,"Logged out user"),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED,"JWT expired"),
    JWT_TOKEN_NOT_EXISTS(HttpStatus.UNAUTHORIZED,"JWT value does not exist in header"),
    // ROLE_NOT_ASSIGNED(HttpStatus.UNAUTHORIZED, "Role not assigned"),
    NOT_AUTHENTICATED_EXCEPTION(HttpStatus.UNAUTHORIZED, "User is not authenticated"),

    // 403 Forbidden


    // 404 NOT FOUND
    EMAIL_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Email not found from OAuth2 provider"),

    // 405 METHOD_NOT_ALLOWED
    METHOD_NOT_ALLOWED_EXCEPTION(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메소드 입니다."),

    // 409 CONFLICT
    DUPLICATED_ID(HttpStatus.CONFLICT, "Failed due to duplicate ID"),


    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
