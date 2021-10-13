package com.zxw.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zxw
 * @date 2021/10/13 11:10
 */
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException{
    private String errorCode;
    private String errorMsg;

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }

}
