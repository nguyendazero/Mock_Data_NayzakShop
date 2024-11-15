package com.haibazo.itsrct.api.mock.dto.response;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiResponseBaseDto<T> {
    private HttpStatus status;
    private Object data;
    private String message;
    private String code;
    private Object meta;

    public ApiResponseBaseDto(HttpStatus status, T data, String message, String code, Object meta) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
        this.meta = meta;
    }

}
