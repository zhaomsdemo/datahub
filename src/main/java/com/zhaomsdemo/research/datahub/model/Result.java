package com.zhaomsdemo.research.datahub.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Result<T> {

    Integer code;
    String message;
    T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }
}
