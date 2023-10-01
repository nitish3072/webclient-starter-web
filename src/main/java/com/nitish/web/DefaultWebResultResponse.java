package com.nitish.web;

import org.springframework.http.HttpStatus;

public class DefaultWebResultResponse<T> implements AbstractWebResultResponse<T> {

    private HttpStatus statusCode;
    private T data;

    public DefaultWebResultResponse(HttpStatus statusCode, T data){
        super();
        this.statusCode = statusCode;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

}

