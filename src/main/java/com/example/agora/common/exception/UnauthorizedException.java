package com.example.agora.common.exception;

import com.example.agora.common.Error;

public class UnauthorizedException extends ApiException{
    public UnauthorizedException(Error error) {
        super(error);
    }
}
