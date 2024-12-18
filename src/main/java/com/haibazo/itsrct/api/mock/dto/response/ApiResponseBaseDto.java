package com.haibazo.itsrct.api.mock.dto.response;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseBaseDto<T> {

    private HttpStatus status;

    private Object data;

    private String message;

    private String code;

    private Pageable meta;

    private Exception error;

}
