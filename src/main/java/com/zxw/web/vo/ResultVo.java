package com.zxw.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2020-12-25 10:02
 */
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = 3525257727440701302L;
    private String code;
    private Object data;
    private String message;
    private long totalCount;
    private String desc;

    public static ResultVo success() {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode("200");
        resultVo.setMessage("请求成功");
        resultVo.setDesc("");
        return resultVo;
    }

    public static ResultVo success(Object data) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode("200");
        resultVo.setMessage("请求成功");
        resultVo.setDesc("");
        return resultVo;
    }

    public static ResultVo success(List data, long totalCount) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode("200");
        resultVo.setMessage("请求成功");
        resultVo.setDesc("");
        resultVo.setData(data);
        resultVo.setTotalCount(totalCount);
        return resultVo;
    }

    public static ResultVo fail(String code, String message) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage(message);
        resultVo.setDesc("");
        return resultVo;
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
