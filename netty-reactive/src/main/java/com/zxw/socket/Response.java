package com.zxw.socket;

import lombok.Data;

import java.io.*;

/**
 * @author zxw
 * @date 2021/11/12 14:00
 */
@Data
public class Response {
    private OutputStream output;
    private static final int BUFFER_SIZE = 1024;
    Request request;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                String errorMessage = "error";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception e) {

        } finally {
            if (fis != null) fis.close();
        }
    }
}
