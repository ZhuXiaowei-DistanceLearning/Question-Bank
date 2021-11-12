package com.zxw.tomcat.threeChapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zxw
 * @date 2021/11/12 17:11
 */
public class HttpConnector implements Runnable {

    boolean stop;
    private String scheme = "http";

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(8083, 1, InetAddress.getByName("127.0.0.1"));) {
            while (!stop) {
                try (Socket socket = serverSocket.accept()) {
                    HttpProcessor processor = new HttpProcessor(this);
                    processor.process(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (IOException e) {
            System.exit(1);
            e.printStackTrace();
        }
    }

}
