package com.booleanuk.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response<T> {

    private String status;
    private T data;


    public void setResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }
}
