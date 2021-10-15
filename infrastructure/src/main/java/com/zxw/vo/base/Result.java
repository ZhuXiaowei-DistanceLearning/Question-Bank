package com.zxw.vo.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zxw
 * @date 2020-12-25 10:02
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 3525257727440701302L;
    private String code;
    private Object data;
    private String message;

    public static <T> Result<T> success() {
        Result<T> result = new Result();
        result.setCode("200");
        result.setMessage("请求成功");
        return result;
    }

    public static <T> Result success(Object data) {
        Result<T> result = new Result();
        result.setCode("200");
        result.setMessage("请求成功");
        return result;
    }

    public static <T> Result success(List<T> data, long totalCount) {
        Result<T> result = new Result();
        result.setCode("200");
        result.setMessage("请求成功");
        result.setData(data);
        return result;
    }

    public static <T> Result fail(String code, String message) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
