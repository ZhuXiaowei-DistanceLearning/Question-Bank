package com.zxw.tomcat.threeChapter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;

/**
 * @author zxw
 * @date 2021/11/12 17:53
 */
public class RequestStream extends ServletInputStream {
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
