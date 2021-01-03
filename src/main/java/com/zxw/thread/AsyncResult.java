package com.zxw.thread;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.Future;

/**
 * @author zxw
 * @date 2021-01-01 21:43
 */
public interface AsyncResult<T> extends Future<T> {
    /**
     * Handle result normally.
     *
     * @param result result.
     */
    void handleResult(T result);

    /**
     * Handle error.
     *
     * @param error error during execution.
     */
    void handleError(Throwable error);

    /**
     * Get result which will be blocked until the result is available or an error occurs.
     */
    T getResult();

    /**
     * Get error if possible.
     *
     * @return error.
     */
    Throwable getError();
}
