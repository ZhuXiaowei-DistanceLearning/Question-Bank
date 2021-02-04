package com.zxw.cglib;

/**
 * @author zxw
 * @date 2021-02-04 10:25
 */
public class ConvertingCursor {
    private Cursor delegate;

    public ConvertingCursor(Cursor delegate) {
        this.delegate = delegate;
    }
}
