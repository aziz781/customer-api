package com.sample.rest.handler;


public class ApiException extends  Exception{

    public ApiException(){}

    public ApiException(String message){
        super(message);
    }

}
