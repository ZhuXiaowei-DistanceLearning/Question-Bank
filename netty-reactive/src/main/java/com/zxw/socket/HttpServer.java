package com.zxw.socket;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zxw
 * @date 2021/11/12 13:49
 */
public class HttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }

    public void await() {
        try (ServerSocket serverSocket = new ServerSocket(8083, 1, InetAddress.getByName("127.0.0.1"));) {
            while (!shutdown) {
                try (Socket socket = serverSocket.accept();
                     InputStream input = socket.getInputStream();
                     OutputStream output = socket.getOutputStream();) {
                    Request request = new Request(input);
                    request.parse();
                    Response response = new Response(output);
                    response.setRequest(request);
                    response.sendStaticResource();
                    shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
