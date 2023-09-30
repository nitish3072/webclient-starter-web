package com.nitish.web;

import org.springframework.http.HttpStatusCode;

public class DefaultWebResultResponse<T> implements AbstractWebResultResponse<T> {

    private HttpStatusCode statusCode;
    private T data;

    public DefaultWebResultResponse(HttpStatusCode statusCode, T data){
        super();
        this.statusCode = statusCode;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}

