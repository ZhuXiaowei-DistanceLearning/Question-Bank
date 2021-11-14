package com.zxw.tomcat.threeChapter.connector;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zxw
 * @date 2021/11/12 17:53
 */
public class RequestStream extends ServletInputStream {

    private InputStream is;

    public RequestStream(InputStream input) {
        this.is = input;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener listener) {

    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
