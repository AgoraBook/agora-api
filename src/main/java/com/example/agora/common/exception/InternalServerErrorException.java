package com.example.agora.common.exception;

import com.example.agora.common.Error;

public class InternalServerErrorException extends ApiException{
    public InternalServerErrorException(Error error){
    super(error);
}
}
