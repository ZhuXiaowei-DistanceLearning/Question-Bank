package com.zxw.aop;

import com.zxw.exception.BusinessException;
import com.zxw.vo.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zxw
 * @date 2021/10/13 11:14
 */
@RestControllerAdvice
@Slf4j
public class ExpHandler {

    @ExceptionHandler({BusinessException.class})
    public Result<Void> handleBusinessException(ServerHttpRequest request, BusinessException e) {
        Result<Void> result = Result.fail(e.getErrorCode(), e.getErrorMsg());
        log.error("//// catch a【{}】 request url :{}, errorCode:{},errorMsg:{}", new Object[]{e.getClass().getSimpleName(), request.getURI(), result.getCode(), result.getMessage(), e});
        return result;
    }

    @ExceptionHandler({Exception.class})
    public Result<Void> handleException(ServerHttpRequest request, Exception e) {
        Result<Void> result = Result.fail("500", "网络异常，请稍后重试！");
        log.error("//// catch a【{}】 request url :{},errorMsg:{},case by ", new Object[]{e.getClass().getSimpleName(), request.getURI(), result.getMessage(), e});
        return result;
    }
}
