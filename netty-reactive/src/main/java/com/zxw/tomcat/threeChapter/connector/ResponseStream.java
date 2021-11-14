package com.zxw.tomcat.threeChapter.connector;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zxw
 * @date 2021-11-13 13:46
 */
public class ResponseStream extends OutputStream {

    private OutputStream outputStream;
    private HttpResponse httpResponse;

    public ResponseStream(OutputStream output) {
        this.outputStream = output;
    }

    public ResponseStream(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    public void write(int b) throws IOException {

    }
}
