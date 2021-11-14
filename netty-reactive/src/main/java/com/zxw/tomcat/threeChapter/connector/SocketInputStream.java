package com.zxw.tomcat.threeChapter.connector;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2021-11-14 11:43
 */
public class SocketInputStream extends InputStream {

    private InputStream inputStream;
    private String[] content;

    public SocketInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        IntStream.range(0, i)
                .forEach(e -> request.append((char) buffer[e]));
        this.content = request.toString().split("\r\n");
        this.parseUri(request.toString());
    }

    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(" ", index1 + 1);
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    public void readHeader(HttpRequest request) {
        for (int i = 1; i < content.length; i++) {
            String header = content[i];
            String[] headerMap = header.split(":");
            request.addHeader(headerMap[0],headerMap[1]);
        }
    }

    public void readLine(HttpRequestLine requestLine) {
        String header = content[0];
        String[] lines = header.split(" ");
        requestLine.setMethod(lines[0]);
        requestLine.setUri(lines[1]);
        requestLine.setProtocol(lines[2]);
    }
}
