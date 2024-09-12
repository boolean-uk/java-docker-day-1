package com.booleanuk.api.response;

public class SuccessResponse<T> extends Response<T>{

    public SuccessResponse(T data) {
        super("success", data);
    }
}
