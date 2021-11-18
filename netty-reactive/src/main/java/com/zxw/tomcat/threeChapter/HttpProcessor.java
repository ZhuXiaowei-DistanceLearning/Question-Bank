package com.zxw.tomcat.threeChapter;

import com.zxw.tomcat.threeChapter.connector.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zxw
 * @date 2021/11/12 17:12
 */
@Slf4j
public class HttpProcessor implements Runnable {
    private boolean stop;
    private HttpConnector connector;
    private HttpRequest request;
    private HttpRequestLine requestLine;

    public HttpProcessor(HttpConnector httpConnector) {
        this.connector = httpConnector;
    }

    public void process(Socket socket) {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream();) {
            request = new HttpRequest(new RequestStream(input));
            HttpResponse response = new HttpResponse(new ResponseStream(output));
            response.setRequest(request);
            response.setHeader("Server", "Servlet Container");
            SocketInputStream socketInputStream = new SocketInputStream(input);
            parseRequest(socketInputStream, output);
            parseHeaders(socketInputStream);
            if (request.getRequestURI().startsWith("/servlet/")) {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(request, response);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseHeaders(SocketInputStream input) {
        input.readHeader(request);
    }

    private void parseRequest(SocketInputStream input, OutputStream output) {
        requestLine = new HttpRequestLine();
        input.readLine(requestLine);
        request.setUri(requestLine.getUri());
    }

    @Override
    public void run() {
        while (!stop) {

        }
    }
}
