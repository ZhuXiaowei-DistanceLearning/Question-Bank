package com.zxw.tomcat.threeChapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zxw
 * @date 2021/11/12 17:12
 */
public class HttpProcessor {
    private HttpConnector connector;

    public HttpProcessor(HttpConnector httpConnector) {
        this.connector = httpConnector;
    }

    public void process(Socket socket) {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream();) {
            HttpRequest request = new HttpRequest(input);
            HttpResponse response = new HttpResponse(output);
            response.setRequest(request);
            response.setHeader("Server", "Servlet Container");
            parseRequest(input, output);
            parseHeaders(input);
            if (request.getRequestURI().startsWith("/servlet/")) {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(request, response);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseHeaders(InputStream input) {

    }

    private void parseRequest(InputStream input, OutputStream output) {
        
    }
}
