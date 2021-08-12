package com.zxw.vo.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author zxw
 * @date 2020-12-25 10:02
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 3525257727440701302L;
    private String code;
    private Object data;
    private String message;
    private long totalCount;
    private String desc;

    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMessage("请求成功");
        result.setDesc("");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode("200");
        result.setMessage("请求成功");
        result.setDesc("");
        return result;
    }

    public static Result success(List data, long totalCount) {
        Result result = new Result();
        result.setCode("200");
        result.setMessage("请求成功");
        result.setDesc("");
        result.setData(data);
        result.setTotalCount(totalCount);
        return result;
    }

    public static Result fail(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setDesc("");
        return result;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", totalCount=" + totalCount +
                ", desc='" + desc + '\'' +
                '}';
    }
}
